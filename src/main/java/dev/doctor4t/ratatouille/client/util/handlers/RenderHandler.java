package dev.doctor4t.ratatouille.client.util.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.doctor4t.ratatouille.client.util.helpers.RenderHelper;
import dev.doctor4t.ratatouille.client.util.systems.rendering.ExtendedShader;
import dev.doctor4t.ratatouille.client.util.systems.rendering.ShaderUniformHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

import java.util.HashMap;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public class RenderHandler {
    public static HashMap<RenderLayer, BufferBuilder> EARLY_BUFFERS = new HashMap<>();
    public static HashMap<RenderLayer, BufferBuilder> BUFFERS = new HashMap<>();
    public static HashMap<RenderLayer, BufferBuilder> LATE_BUFFERS = new HashMap<>();
    public static HashMap<RenderLayer, ShaderUniformHandler> HANDLERS = new HashMap<>();
    public static VertexConsumerProvider.Immediate EARLY_DELAYED_RENDER;
    public static VertexConsumerProvider.Immediate DELAYED_RENDER;
    public static VertexConsumerProvider.Immediate LATE_DELAYED_RENDER;
    public static Matrix4f PARTICLE_MATRIX = null;

    public static void initialize() {
        EARLY_DELAYED_RENDER = VertexConsumerProvider.immediate(EARLY_BUFFERS, new BufferBuilder(FabricLoader.getInstance().isModLoaded("sodium") ? 262144 : 256));
        DELAYED_RENDER = VertexConsumerProvider.immediate(BUFFERS, new BufferBuilder(FabricLoader.getInstance().isModLoaded("sodium") ? 2097152 : 256));
        LATE_DELAYED_RENDER = VertexConsumerProvider.immediate(LATE_BUFFERS, new BufferBuilder(FabricLoader.getInstance().isModLoaded("sodium") ? 262144 : 256));
    }

    public static void renderLast(MatrixStack stack) {
        stack.push();

        RenderSystem.getModelViewStack().push();
        RenderSystem.getModelViewStack().loadIdentity();
        if (PARTICLE_MATRIX != null) {
            RenderSystem.getModelViewStack().multiplyPositionMatrix(PARTICLE_MATRIX);
        }
        RenderSystem.applyModelViewMatrix();
        RenderSystem.getModelViewStack().pop();
        RenderSystem.applyModelViewMatrix();

        draw(EARLY_DELAYED_RENDER, EARLY_BUFFERS);
        draw(DELAYED_RENDER, BUFFERS);
        draw(LATE_DELAYED_RENDER, LATE_BUFFERS);
        stack.pop();
    }

    public static void draw(VertexConsumerProvider.Immediate source, HashMap<RenderLayer, BufferBuilder> buffers) {
        for (RenderLayer type : buffers.keySet()) {
            ShaderProgram instance = RenderHelper.getShader(type);
            if (HANDLERS.containsKey(type)) {
                ShaderUniformHandler handler = HANDLERS.get(type);
                handler.updateShaderData(instance);
            }
            source.draw(type);
            if (instance instanceof ExtendedShader extendedShaderInstance) {
                extendedShaderInstance.setUniformDefaults();
            }
        }
        source.draw();
    }

    public static void addRenderLayer(RenderLayer type) {
        RenderHandler.EARLY_BUFFERS.put(type, new BufferBuilder(type.getExpectedBufferSize()));
        RenderHandler.BUFFERS.put(type, new BufferBuilder(type.getExpectedBufferSize()));
        RenderHandler.LATE_BUFFERS.put(type, new BufferBuilder(type.getExpectedBufferSize()));
    }
}
