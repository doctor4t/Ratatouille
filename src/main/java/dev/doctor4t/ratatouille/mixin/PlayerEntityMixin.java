package dev.doctor4t.ratatouille.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.ratatouille.components.RatatouilleComponents;
import dev.doctor4t.ratatouille.components.RecoveryPosComponent;
import dev.doctor4t.ratatouille.entity.PlayerHeadEntity;
import dev.doctor4t.ratatouille.util.SupporterUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    @Final
    private PlayerInventory inventory;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    protected abstract void vanishCursedItems();

    @Shadow
    public abstract void playSound(SoundEvent sound, float volume, float pitch);

    @Inject(method = "dropInventory", at = @At("HEAD"), cancellable = true)
    protected void ratatouille$spawnSkullOnDeath(CallbackInfo ci) {
        if (!this.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            this.vanishCursedItems();

            boolean hasItems = false;
            int i = 0;
            while (i < inventory.size() && !hasItems) {
                if (!inventory.getStack(i).isEmpty()) {
                    hasItems = true;
                }

                i++;
            }

            // check for trinkets too
//            if (!hasItems && TrinketsApi.getTrinketComponent(this).isPresent()) {
//                for (Pair<SlotReference, ItemStack> slotReferenceItemStackPair : TrinketsApi.getTrinketComponent(this).get().getAllEquipped()) {
//                    if (!slotReferenceItemStackPair.getRight().isEmpty()) {
//                        hasItems = true;
//                        break;
//                    }
//                }
//            }

            if (hasItems) {
                PlayerHeadEntity playerHeadEntity = new PlayerHeadEntity(this.getWorld(), PlayerEntity.class.cast(this));
                this.getWorld().spawnEntity(playerHeadEntity);

                // clear trinket slots
//                TrinketsApi.getTrinketComponent(this).ifPresent(trinkets -> trinkets.forEach((ref, stack) -> ref.inventory().setStack(ref.index(), ItemStack.EMPTY)));

                ci.cancel();
            }
        }
    }

    @Inject(method = "getLastDeathPos", at = @At("HEAD"), cancellable = true)
    private void contentTweaks$newDeathPos(CallbackInfoReturnable<Optional<GlobalPos>> cir) {
        RecoveryPosComponent pos = RatatouilleComponents.RECOVERY_POS_COMPONENT.get(this);
        pos.getDeathPos().ifPresent(deathPos -> cir.setReturnValue(Optional.of(GlobalPos.create(this.getWorld().getRegistryKey(), deathPos))));
    }

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    public Text ratatouille$styliseSupporterNames(Text original) {
        return SupporterUtils.getSupporterStylisedName(this.getUuid(), original);
    }

}
