/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.doctor4t.ratatouille.mixin.client.render;

import dev.doctor4t.ratatouille.client.util.systems.rendering.ExtendedShader;
import net.minecraft.client.gl.ShaderProgram;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/*
    Original mixin based on the Quilt port (arathain) of the Lodestone library (Sammy; and Lodestar)
 */
@Mixin(ShaderProgram.class)
abstract class ShaderMixin {
    @Shadow
    @Final
    private String name;

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;<init>(Ljava/lang/String;)V"), allow = 1)
    private String modifyProgramId(String id) {
        if ((Object) this instanceof ExtendedShader) {
            return ExtendedShader.rewriteAsId(id, name);
        }

        return id;
    }
}
