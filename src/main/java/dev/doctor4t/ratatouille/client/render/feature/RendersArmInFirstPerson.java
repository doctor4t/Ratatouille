package dev.doctor4t.ratatouille.client.render.feature;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public interface RendersArmInFirstPerson<T extends LivingEntity> {
    boolean isFeatureEnabled(AbstractClientPlayerEntity livingEntity);

    BipedEntityModel<T> getModel(LivingEntity livingEntity);

    Identifier getTexture(LivingEntity livingEntity);

    ModelPart getRightArm(LivingEntity livingEntity);

    ModelPart getLeftArm(LivingEntity livingEntity);
}
