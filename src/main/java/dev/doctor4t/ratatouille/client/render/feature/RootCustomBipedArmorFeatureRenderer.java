package dev.doctor4t.ratatouille.client.render.feature;

import dev.doctor4t.ratatouille.client.model.armor.CustomBipedArmorModel;
import dev.doctor4t.ratatouille.client.util.CustomModelArmorUtil;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RootCustomBipedArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> implements RendersArmInFirstPerson<T> {
    public final Map<CustomModelArmorUtil.SetItems, CustomBipedArmorModel<LivingEntity>> customArmorModels;

    public RootCustomBipedArmorFeatureRenderer(FeatureRendererContext<T, M> context, EntityRendererFactory.Context loader) {
        super(context);

        this.customArmorModels = new HashMap<>();
        for (CustomModelArmorUtil.SetItems setItems : CustomModelArmorUtil.CUSTOM_ARMOR_MODELS.keySet()) {
            this.customArmorModels.put(setItems, CustomModelArmorUtil.CUSTOM_ARMOR_MODELS.get(setItems).modelConstructor().apply(loader));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        for (CustomModelArmorUtil.SetItems customArmorSetItems : customArmorModels.keySet()) {
            CustomBipedArmorModel<T> customArmorModel = (CustomBipedArmorModel<T>) customArmorModels.get(customArmorSetItems);

            // rotations
            this.getContextModel().copyBipedStateTo(customArmorModel);

            // check visibility
            boolean shouldDisplayHelmet = customArmorSetItems.shouldDisplayHelmet(entity);
            boolean shouldDisplayChestplate = customArmorSetItems.shouldDisplayChestplate(entity);
            boolean shouldDisplayLeggings = customArmorSetItems.shouldDisplayLeggings(entity);
            boolean shouldDisplayBoots = customArmorSetItems.shouldDisplayBoots(entity);

            // apply visibility
            customArmorModel.helmet.visible = shouldDisplayHelmet;

            customArmorModel.body_chestplate.visible = shouldDisplayChestplate;
            customArmorModel.right_arm_chestplate.visible = shouldDisplayChestplate;
            customArmorModel.left_arm_chestplate.visible = shouldDisplayChestplate;

            customArmorModel.body_leggings.visible = shouldDisplayLeggings;
            customArmorModel.right_leg_leggings.visible = shouldDisplayLeggings;
            customArmorModel.left_leg_leggings.visible = shouldDisplayLeggings;

            customArmorModel.right_leg_boot.visible = shouldDisplayBoots;
            customArmorModel.left_leg_boot.visible = shouldDisplayBoots;

            // render
            customArmorModel.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(customArmorModel.getTexture())), light, OverlayTexture.DEFAULT_UV);
        }
    }

    @Override
    public CustomBipedArmorModel<T> getModel(LivingEntity livingEntity) {
        for (CustomModelArmorUtil.SetItems customArmorSetItems : customArmorModels.keySet()) {
            if (customArmorSetItems.shouldDisplayChestplate(livingEntity)) {
                return (CustomBipedArmorModel<T>) customArmorModels.get(customArmorSetItems);
            }
        }

        return null;
    }

    @Override
    public boolean isFeatureEnabled(AbstractClientPlayerEntity livingEntity) {
        return this.getModel(livingEntity) != null;
    }

    @Override
    public Identifier getTexture(LivingEntity livingEntity) {
        return this.getModel(livingEntity).getTexture();
    }
}