package dev.doctor4t.ratatouille.mixin.client.render;


import dev.doctor4t.ratatouille.client.lib.render.systems.postprocess.RatatouilleGlslPreprocessor;
import net.minecraft.client.gl.EffectShaderStage;
import net.minecraft.client.gl.GlImportProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/*
    Original mixin based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
@Mixin(EffectShaderStage.class)
public class EffectProgramMixin {
    @ModifyArg(method = "createFromResource", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/EffectShaderStage;load(Lnet/minecraft/client/gl/ShaderStage$Type;Ljava/lang/String;Ljava/io/InputStream;Ljava/lang/String;Lnet/minecraft/client/gl/GlImportProcessor;)I"), index = 4)
    private static GlImportProcessor useCustomPreprocessor(GlImportProcessor org) {
        return new RatatouilleGlslPreprocessor();
    }
}
