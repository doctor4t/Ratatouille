package dev.doctor4t.ratatouille.client.util.setup;

import com.mojang.datafixers.util.Pair;
import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.util.handlers.RenderHandler;
import dev.doctor4t.ratatouille.client.util.systems.rendering.Phases;
import dev.doctor4t.ratatouille.client.util.systems.rendering.ShaderUniformHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.function.Function;

import static net.minecraft.client.render.VertexFormats.*;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public class RatatouilleRenderLayers extends RenderPhase {
    /**
     * Stores many copies of render types, a copy is a new instance of a render type with the same properties.
     * It's useful when we want to apply different uniform changes with each separate use of our render type.
     * Use the {@link #copy(int, RenderLayer)} method to create copies.
     */
    public static final HashMap<Pair<Integer, RenderLayer>, RenderLayer> COPIES = new HashMap<>();
    public static final RenderLayer ADDITIVE_BLOCK = createGenericRenderLayer(Ratatouille.MOD_ID, "block", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.ADDITIVE_TEXTURE.phase, Phases.ADDITIVE_TRANSPARENCY, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
    public static final RenderLayer ADDITIVE_SOLID = createGenericRenderLayer(Ratatouille.MOD_ID, "additive_solid", POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM, Phases.ADDITIVE_TRANSPARENCY);
    public static final RenderLayer TRANSPARENT_BLOCK = createGenericRenderLayer(Ratatouille.MOD_ID, "transparent_block", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM, Phases.NORMAL_TRANSPARENCY, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
    public static final RenderLayer TRANSPARENT_SOLID = createGenericRenderLayer(Ratatouille.MOD_ID, "transparent_solid", POSITION_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM, Phases.NORMAL_TRANSPARENCY);
    public static final RenderLayer OUTLINE_SOLID = createGenericRenderLayer(Ratatouille.MOD_ID, "outline_solid", POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, RatatouilleShaders.ADDITIVE_TEXTURE.phase, Phases.ADDITIVE_TRANSPARENCY, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
    public static final RenderLayer VERTEX_DISTORTION = createGenericRenderLayer(Ratatouille.MOD_ID, "vertex_distortion", POSITION_TEXTURE_COLOR_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.VERTEX_DISTORTION.phase, Phases.ADDITIVE_TRANSPARENCY, SpriteAtlasTexture.PARTICLE_ATLAS_TEXTURE);
    /**
     * Render Functions. You can create Render Types by statically applying these to your texture. Alternatively, use {@link #GENERIC} if none of the presets suit your needs.
     */


    public static final RenderLayerProvider TEXTURE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "texture", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RenderPhase.POSITION_COLOR_LIGHTMAP_PROGRAM, Phases.NO_TRANSPARENCY, texture));
    public static final RenderLayerProvider TRANSPARENT_TEXTURE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "transparent_texture", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RenderPhase.POSITION_COLOR_TEXTURE_LIGHTMAP_PROGRAM, Phases.NORMAL_TRANSPARENCY, texture));
    public static final RenderLayerProvider TRANSPARENT_TEXTURE_TRIANGLE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "transparent_texture_triangle", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.TRIANGLES, RenderPhase.POSITION_COLOR_TEXTURE_LIGHTMAP_PROGRAM, Phases.NORMAL_TRANSPARENCY, texture));
    public static final RenderLayerProvider ADDITIVE_TEXTURE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "additive_texture", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.ADDITIVE_TEXTURE.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider ADDITIVE_TEXTURE_TRIANGLE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "additive_texture_triangle", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.TRIANGLES, RenderPhase.POSITION_COLOR_TEXTURE_LIGHTMAP_PROGRAM, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider VERTEX_DISTORTION_TEXTURE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "vertex_distortion_texture", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.VERTEX_DISTORTION.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider RADIAL_NOISE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "radial_noise", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.RADIAL_NOISE.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider RADIAL_SCATTER_NOISE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "radial_scatter_noise", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.RADIAL_SCATTER_NOISE.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider SCROLLING_TEXTURE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "scrolling_texture", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.SCROLLING_TEXTURE.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final RenderLayerProvider SCROLLING_TEXTURE_TRIANGLE = new RenderLayerProvider((texture) -> createGenericRenderLayer(texture.getNamespace(), "scrolling_texture_triangle", POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, RatatouilleShaders.SCROLLING_TRIANGLE_TEXTURE.phase, Phases.ADDITIVE_TRANSPARENCY, texture));
    public static final Function<RenderLayerData, RenderLayer> GENERIC = (data) -> createGenericRenderLayer(data.name, data.format, data.mode, data.shader, data.transparency, data.texture);

    public RatatouilleRenderLayers(String string, Runnable runnable, Runnable runnable2) {
        super(string, runnable, runnable2);
    }

    public static void yea() {
    }

    /**
     * Creates a custom render type with a texture.
     */
    public static RenderLayer createGenericRenderLayer(String modid, String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, Transparency transparency, Identifier texture) {
        return createGenericRenderLayer(modid + ":" + name, format, mode, shader, transparency, new Texture(texture, false, false));
    }

    public static RenderLayer createGenericRenderLayer(String modid, String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, Transparency transparency, TextureBase texture) {
        return createGenericRenderLayer(modid + ":" + name, format, mode, shader, transparency, texture);
    }

    /**
     * Creates a custom render type with an empty texture.
     */
    public static RenderLayer createGenericRenderLayer(String modid, String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, Transparency transparency) {
        return createGenericRenderLayer(modid + ":" + name, format, mode, shader, transparency, RenderPhase.NO_TEXTURE);
    }

    /**
     * Creates a custom render type and creates a buffer builder for it.
     */
    public static RenderLayer createGenericRenderLayer(String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, Transparency transparency, TextureBase texture) {
        RenderLayer type = RenderLayer.of(
                name, format, mode, FabricLoader.getInstance().isModLoaded("sodium") ? 2097152 : 256, false, false, RenderLayer.MultiPhaseParameters.builder()
                        .program(shader)
                        .writeMaskState(new WriteMaskState(true, true))
                        .lightmap(new Lightmap(false))
                        .transparency(transparency)
                        .texture(texture)
                        .cull(new Cull(true))
                        .build(true)
        );
        RenderHandler.addRenderLayer(type);
        return type;
    }

    /**
     * Queues shader uniform changes for a render layer. When we end batches in {@link RenderHandler#renderLast(net.minecraft.client.util.math.MatrixStack)}, we do so one render layer at a time.
     * Prior to ending a batch, we run {@link ShaderUniformHandler#updateShaderData(net.minecraft.client.render.Shader)} if one is present for a given render layer.
     */
    public static RenderLayer queueUniformChanges(RenderLayer type, ShaderUniformHandler handler) {
        RenderHandler.HANDLERS.put(type, handler);
        return type;
    }

    /**
     * Creates a copy of a render type. These are stored in the {@link #COPIES} hashmap, with the key being a pair of original render type and copy index.
     */
    public static RenderLayer copy(int index, RenderLayer type) {
        return COPIES.computeIfAbsent(Pair.of(index, type), (p) -> GENERIC.apply(new RenderLayerData((RenderLayer.MultiPhase) type)));
    }

    /*
     * This needs to be rewritten, but I don't have the brainpower to do it rn
     * */
    public static RenderLayer getOutlineTranslucent(Identifier texture, boolean cull) {
        return RenderLayer.of(Ratatouille.MOD_ID + ":outline_translucent",
                VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, FabricLoader.getInstance().isModLoaded("sodium") ? 262144 : 256, false, true, RenderLayer.MultiPhaseParameters.builder()
                        .program(cull ? ENTITY_TRANSLUCENT_CULL_PROGRAM : ENTITY_TRANSLUCENT_PROGRAM)
                        .texture(new Texture(texture, false, false))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .cull(cull ? ENABLE_CULLING : DISABLE_CULLING)
                        .lightmap(ENABLE_LIGHTMAP)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .writeMaskState(COLOR_MASK)
                        .build(false));
    }

    /**
     * Stores all relevant data from a RenderLayer.
     */
    public static class RenderLayerData {
        public final String name;
        public final VertexFormat format;
        public final VertexFormat.DrawMode mode;
        public final ShaderProgram shader;
        public final TextureBase texture;
        public Transparency transparency = Phases.ADDITIVE_TRANSPARENCY;

        public RenderLayerData(String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, TextureBase texture) {
            this.name = name;
            this.format = format;
            this.mode = mode;
            this.shader = shader;
            this.texture = texture;
        }

        public RenderLayerData(String name, VertexFormat format, VertexFormat.DrawMode mode, ShaderProgram shader, Transparency transparency, TextureBase texture) {
            this(name, format, mode, shader, texture);
            this.transparency = transparency;
        }

        public RenderLayerData(RenderLayer.MultiPhase type) {
            this(type.name, type.getVertexFormat(), type.getDrawMode(), type.phases.program, type.phases.transparency, type.phases.texture);
        }
    }

    public static class RenderLayerProvider {
        private final Function<Identifier, RenderLayer> function;
        private final Function<Identifier, RenderLayer> memorizedFunction;

        public RenderLayerProvider(Function<Identifier, RenderLayer> function) {
            this.function = function;
            this.memorizedFunction = Util.memoize(function);
        }

        public RenderLayer apply(Identifier texture) {
            return function.apply(texture);
        }

        public RenderLayer applyAndCache(Identifier texture) {
            return this.memorizedFunction.apply(texture);
        }
    }
}
