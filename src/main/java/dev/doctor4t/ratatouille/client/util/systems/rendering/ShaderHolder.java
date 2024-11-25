package dev.doctor4t.ratatouille.client.util.systems.rendering;


import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.texture.SpriteAtlasTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public class ShaderHolder {
    public ExtendedShader instance;
    public final RenderPhase.ShaderProgram phase = new RenderPhase.ShaderProgram(getInstance());
    public ArrayList<String> uniforms;
    public ArrayList<UniformData> defaultUniformData = new ArrayList<>();

    public ShaderHolder(String... uniforms) {
        this.uniforms = new ArrayList<>(List.of(uniforms));
    }

    public void setUniformDefaults() {
        RenderSystem.setShaderTexture(1, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        defaultUniformData.forEach(u -> u.setUniformValue(instance.getUniformOrDefault(u.uniformName)));
    }

    public Supplier<ShaderProgram> getInstance() {
        return () -> instance;
    }

    public void setInstance(ExtendedShader instance) {
        this.instance = instance;
    }
}
