package dev.doctor4t.ratatouille.client.render.feature;

import dev.doctor4t.ratatouille.client.model.armor.CustomBipedArmorModel;
import dev.doctor4t.ratatouille.client.util.ArmorDisplayConditions;
import dev.doctor4t.ratatouille.client.util.CustomModelArmorUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RootCustomBipedArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> implements RendersArmInFirstPerson<T> {
    public final Map<ArmorDisplayConditions, CustomBipedArmorModel<LivingEntity>> customArmorModels;

    public RootCustomBipedArmorFeatureRenderer(FeatureRendererContext<T, M> context, EntityRendererFactory.Context loader) {
        super(context);

        this.customArmorModels = new HashMap<>();
        for (ArmorDisplayConditions displayConditions : CustomModelArmorUtil.CUSTOM_ARMOR_MODELS.keySet()) {
            this.customArmorModels.put(displayConditions, CustomModelArmorUtil.CUSTOM_ARMOR_MODELS.get(displayConditions).modelConstructor().apply(loader));
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        for (ArmorDisplayConditions customArmorSetConditions : customArmorModels.keySet()) {
            CustomBipedArmorModel<T> customArmorModel = (CustomBipedArmorModel<T>) customArmorModels.get(customArmorSetConditions);

            this.getContextModel().copyBipedStateTo(customArmorModel);

            RenderLayer translucentRenderLayer = RenderLayer.getEntityTranslucent(customArmorModel.getTexture());
            VertexConsumer helmetBuffer = ItemRenderer.getItemGlintConsumer(vertexConsumers, translucentRenderLayer, false, customArmorSetConditions.shouldDisplayHelmetGlint(entity));
            VertexConsumer chestplateBuffer = ItemRenderer.getItemGlintConsumer(vertexConsumers, translucentRenderLayer, false, customArmorSetConditions.shouldDisplayChestplateGlint(entity));
            VertexConsumer leggingsBuffer = ItemRenderer.getItemGlintConsumer(vertexConsumers, translucentRenderLayer, false, customArmorSetConditions.shouldDisplayLeggingsGlint(entity));
            VertexConsumer bootsBuffer = ItemRenderer.getItemGlintConsumer(vertexConsumers, translucentRenderLayer, false, customArmorSetConditions.shouldDisplayBootsGlint(entity));

            if (customArmorSetConditions.shouldDisplayHelmet(entity)) customArmorModel.renderHelmet(matrices, helmetBuffer, light, OverlayTexture.DEFAULT_UV);
            if (customArmorSetConditions.shouldDisplayChestplate(entity)) customArmorModel.renderChestplate(matrices, chestplateBuffer, light, OverlayTexture.DEFAULT_UV);
            if (customArmorSetConditions.shouldDisplayLeggings(entity)) customArmorModel.renderLeggings(matrices, leggingsBuffer, light, OverlayTexture.DEFAULT_UV);
            if (customArmorSetConditions.shouldDisplayBoots(entity)) customArmorModel.renderBoots(matrices, bootsBuffer, light, OverlayTexture.DEFAULT_UV);


        }
    }

    @Override
    public CustomBipedArmorModel<T> getModel(LivingEntity livingEntity) {
        for (ArmorDisplayConditions displayConditions : customArmorModels.keySet()) {
            if (displayConditions.shouldDisplayChestplate(livingEntity)) {
                return (CustomBipedArmorModel<T>) customArmorModels.get(displayConditions);
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