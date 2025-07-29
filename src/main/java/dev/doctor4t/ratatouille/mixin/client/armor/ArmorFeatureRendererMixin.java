package dev.doctor4t.ratatouille.mixin.client.armor;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.doctor4t.ratatouille.client.util.CustomModelArmorUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
        super(context);
    }

    /*
    This mixin disables the Vanilla armor rendering, as equipping any custom armor piece leads the game to look for a texture for the default armor model.
    Since we are using a custom model, we don't want the Vanilla armor model to display with missing textures alongside our custom model.
     */
    @WrapOperation(method = "renderArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/ArmorFeatureRenderer;renderArmorParts(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/model/BipedEntityModel;ILnet/minecraft/util/Identifier;)V"))
    private void hadopelagic$cancelVanillaArmorRendering(ArmorFeatureRenderer<T, M, A> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, int j, Identifier identifier, Operation<Void> original, @Local(argsOnly = true) T entity, @Local(argsOnly = true) EquipmentSlot armorSlot) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (CustomModelArmorUtil.CUSTOM_ARMOR_ITEMS.contains(itemStack.getItem())) {
            original.call(instance, matrices, vertexConsumers, light, model, j, identifier);
        }
    }
}
