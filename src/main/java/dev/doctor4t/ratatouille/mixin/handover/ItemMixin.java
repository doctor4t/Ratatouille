package dev.doctor4t.ratatouille.mixin.handover;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    public void ratatouille$handItemOver(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (player.isInCreativeMode() && player.isSneaking()) {
            EquipmentSlot equipmentSlot = entity.getPreferredEquipmentSlot(stack);
            player.getStackInHand(hand).decrement(1);

            if (entity.hasStackEquipped(equipmentSlot)) {
                player.giveItemStack(entity.getEquippedStack(equipmentSlot));
            }

            entity.equipStack(equipmentSlot, stack);

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }

}
