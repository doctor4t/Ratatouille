package dev.doctor4t.ratatouille.client.lib.render.systems.postprocess;

import com.google.common.collect.Lists;
import com.google.gson.JsonParseException;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.*;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static com.mojang.blaze3d.platform.GlConst.GL_DRAW_FRAMEBUFFER;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public abstract class PostProcessor {
    protected static final MinecraftClient MC = MinecraftClient.getInstance();
    /**
     * Being updated every frame before calling applyPostProcess() by PostProcessHandler
     */
    public static MatrixStack viewModelStack;
    public static final Collection<Pair<String, Consumer<GlUniform>>> COMMON_UNIFORMS = Lists.newArrayList(
            Pair.of("cameraPos", u -> u.set(MC.gameRenderer.getCamera().getPos().toVector3f())),
            Pair.of("lookVector", u -> u.set(MC.gameRenderer.getCamera().getHorizontalPlane())),
            Pair.of("upVector", u -> u.set(MC.gameRenderer.getCamera().getVerticalPlane())),
            Pair.of("leftVector", u -> u.set(MC.gameRenderer.getCamera().getDiagonalPlane())),
            Pair.of("invViewMat", u -> {
                Matrix4f invertedViewMatrix = new Matrix4f(PostProcessor.viewModelStack.peek().getPositionMatrix());
                invertedViewMatrix.invert();
                u.set(invertedViewMatrix);
            }),
            Pair.of("invProjMat", u -> {
                Matrix4f invertedProjectionMatrix = new Matrix4f(RenderSystem.getProjectionMatrix());
                invertedProjectionMatrix.invert();
                u.set(invertedProjectionMatrix);
            }),
            Pair.of("nearPlaneDistance", u -> u.set(GameRenderer.CAMERA_DEPTH)),
            Pair.of("farPlaneDistance", u -> u.set(MC.gameRenderer.getFarPlaneDistance())),
            Pair.of("fov", u -> u.set((float) Math.toRadians(MC.gameRenderer.getFov(MC.gameRenderer.getCamera(), MC.getTickDelta(), true)))),
            Pair.of("aspectRatio", u -> u.set((float) MC.getWindow().getWidth() / (float) MC.getWindow().getHeight()))
    );
    protected PostEffectProcessor shaderEffect;
    protected JsonEffectShaderProgram[] effects;
    protected double time;
    private boolean initialized = false;
    private Framebuffer tempDepthBuffer;
    private Collection<Pair<GlUniform, Consumer<GlUniform>>> defaultUniforms;
    private boolean isActive = true;

    /**
     * Example: "octus:foo" points to octus:shaders/post/foo.json
     */
    public abstract Identifier getPostEffectProcessorId();

    public void init() {
        loadPostChain();

        if (shaderEffect != null) {
            tempDepthBuffer = shaderEffect.getSecondaryTarget("depthMain");

            defaultUniforms = new ArrayList<>();
            for (JsonEffectShaderProgram e : effects) {
                for (Pair<String, Consumer<GlUniform>> pair : COMMON_UNIFORMS) {
                    GlUniform u = e.getUniformByName(pair.getFirst());
                    if (u != null) {
                        defaultUniforms.add(Pair.of(u, pair.getSecond()));
                    }
                }
            }
        }

        initialized = true;
    }

    /**
     * Load or reload the shader
     */
    public final void loadPostChain() {
        if (shaderEffect != null) {
            shaderEffect.close();
            shaderEffect = null;
        }

        try {
            Identifier file = getPostEffectProcessorId();
            file = new Identifier(file.getNamespace(), "shaders/post/" + file.getPath() + ".json");
            shaderEffect = new PostEffectProcessor(
                    MC.getTextureManager(),
                    MC.getResourceManager(),
                    MC.getFramebuffer(),
                    file
            );
            shaderEffect.setupDimensions(MC.getWindow().getWidth(), MC.getWindow().getHeight());
            effects = shaderEffect.passes.stream().map(PostEffectPass::getProgram).toArray(JsonEffectShaderProgram[]::new);
        } catch (IOException | JsonParseException e) {
        }
    }

    public final void copyDepthBuffer() {
        if (isActive) {
            if (shaderEffect == null || tempDepthBuffer == null) return;

            tempDepthBuffer.copyDepthFrom(MC.getFramebuffer());

            // rebind the main framebuffer so that we don't mess up other things
            GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, MC.getFramebuffer().fbo);
        }
    }

    public void resize(int width, int height) {
        if (shaderEffect != null) {
            shaderEffect.setupDimensions(width, height);
            if (tempDepthBuffer != null)
                tempDepthBuffer.resize(width, height, MinecraftClient.IS_SYSTEM_MAC);
        }
    }

    private void applyDefaultUniforms() {
        Arrays.stream(effects).forEach(e -> e.getUniformByNameOrDummy("time").set((float) time));

        defaultUniforms.forEach(pair -> pair.getSecond().accept(pair.getFirst()));
    }

    public final void applyPostProcess() {
        if (isActive) {
            if (!initialized)
                init();

            if (shaderEffect != null) {
                time += MC.getLastFrameDuration() / 20.0;

                applyDefaultUniforms();

                beforeProcess(viewModelStack);
                if (!isActive) return;
                shaderEffect.render(MC.getTickDelta());

                GlStateManager._glBindFramebuffer(GL_DRAW_FRAMEBUFFER, MC.getFramebuffer().fbo);
                afterProcess();
            }
        }
    }

    /**
     * Set uniforms and bind textures here
     */
    public abstract void beforeProcess(MatrixStack viewModelStack);

    /**
     * Unbind textures
     */
    public abstract void afterProcess();

    public final boolean isActive() {
        return isActive;
    }

    public final void setActive(boolean active) {
        this.isActive = active;

        if (!active)
            time = 0.0;
    }
}
