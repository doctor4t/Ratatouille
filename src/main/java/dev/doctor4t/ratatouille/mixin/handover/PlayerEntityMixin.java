package dev.doctor4t.ratatouille.mixin.handover;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At("HEAD"))
    public void attack(Entity target, CallbackInfo ci) {
        if (this.isInCreativeMode() && this.isSneaking() && this.getMainHandStack().isEmpty() && target instanceof LivingEntity livingEntity) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                livingEntity.dropStack(livingEntity.getEquippedStack(slot));
                livingEntity.equipStack(slot, ItemStack.EMPTY);
            }
        }
    }
}
