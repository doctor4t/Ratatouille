package dev.doctor4t.ratatouille.client.util.setup;

import com.mojang.datafixers.util.Pair;
import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.util.systems.rendering.ExtendedShader;
import dev.doctor4t.ratatouille.client.util.systems.rendering.ShaderHolder;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.resource.ResourceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public class RatatouilleShaders {
    public static List<Pair<ShaderProgram, Consumer<ShaderProgram>>> shaderList;
    public static ShaderHolder ADDITIVE_TEXTURE = new ShaderHolder();
    public static ShaderHolder LODESTONE_PARTICLE = new ShaderHolder();
    public static ShaderHolder ADDITIVE_PARTICLE = new ShaderHolder();

    public static ShaderHolder MASKED_TEXTURE = new ShaderHolder();
    public static ShaderHolder DISTORTED_TEXTURE = new ShaderHolder("Speed", "TimeOffset", "Intensity", "XFrequency", "YFrequency", "UVCoordinates");
    public static ShaderHolder METALLIC_NOISE = new ShaderHolder("Intensity", "Size", "Speed", "Brightness");
    public static ShaderHolder RADIAL_NOISE = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");
    public static ShaderHolder RADIAL_SCATTER_NOISE = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");

    public static ShaderHolder VERTEX_DISTORTION = new ShaderHolder();
    //public static ShaderHolder BLOOM = new ShaderHolder();

    public static ShaderHolder SCROLLING_TEXTURE = new ShaderHolder("Speed");
    public static ShaderHolder TRIANGLE_TEXTURE = new ShaderHolder();
    public static ShaderHolder COLOR_GRADIENT_TEXTURE = new ShaderHolder("DarkColor");
    public static ShaderHolder SCROLLING_TRIANGLE_TEXTURE = new ShaderHolder("Speed");

    public static void init(ResourceFactory factory) throws IOException {
        shaderList = new ArrayList<>();
        registerShader(ExtendedShader.createShaderInstance(LODESTONE_PARTICLE, factory, Ratatouille.id("particle"), VertexFormats.POSITION_TEXTURE_COLOR_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(ADDITIVE_TEXTURE, factory, Ratatouille.id("additive_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(ADDITIVE_PARTICLE, factory, Ratatouille.id("additive_particle"), VertexFormats.POSITION_TEXTURE_COLOR_LIGHT));

        registerShader(ExtendedShader.createShaderInstance(DISTORTED_TEXTURE, factory, Ratatouille.id("noise/distorted_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(METALLIC_NOISE, factory, Ratatouille.id("noise/metallic"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(RADIAL_NOISE, factory, Ratatouille.id("noise/radial_noise"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(RADIAL_SCATTER_NOISE, factory, Ratatouille.id("noise/radial_scatter_noise"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));

        registerShader(ExtendedShader.createShaderInstance(SCROLLING_TEXTURE, factory, Ratatouille.id("vfx/scrolling_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(TRIANGLE_TEXTURE, factory, Ratatouille.id("vfx/triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
        registerShader(ExtendedShader.createShaderInstance(SCROLLING_TRIANGLE_TEXTURE, factory, Ratatouille.id("vfx/scrolling_triangle_texture"), VertexFormats.POSITION_COLOR_TEXTURE_LIGHT));
    }

    public static void registerShader(ExtendedShader extendedShaderInstance) {
        registerShader(extendedShaderInstance, (shader) -> ((ExtendedShader) shader).getHolder().setInstance((ExtendedShader) shader));
    }

    public static void registerShader(ShaderProgram shader, Consumer<ShaderProgram> onLoaded) {
        shaderList.add(Pair.of(shader, onLoaded));
    }
}
