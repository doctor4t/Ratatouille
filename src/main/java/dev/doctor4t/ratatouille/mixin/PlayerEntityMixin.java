package dev.doctor4t.ratatouille.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.ratatouille.util.SupporterUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    public Text ratatouille$styliseSupporterNames(Text original) {
        return SupporterUtils.getSupporterStylisedName(this.getUuid(), original);
    }
}
