package dev.doctor4t.ratatouille.client.util.systems.rendering;

import net.minecraft.client.gl.ShaderProgram;

/*
    Original class based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
public interface ShaderUniformHandler {
    void updateShaderData(ShaderProgram instance);
}
