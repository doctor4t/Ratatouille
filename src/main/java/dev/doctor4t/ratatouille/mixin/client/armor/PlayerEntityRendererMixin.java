package dev.doctor4t.ratatouille.mixin.client.armor;

import dev.doctor4t.ratatouille.client.render.feature.RendersArmInFirstPerson;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRendererMixin<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    protected PlayerEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Shadow
    protected abstract void setModelPose(AbstractClientPlayerEntity player);

    @Inject(method = "renderRightArm", at = @At(value = "HEAD"))
    public void hadopelagic$renderArmorRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
        renderArmorArm(matrices, vertexConsumers, light, player, true);
    }

    @Inject(method = "renderLeftArm", at = @At(value = "HEAD"))
    public void hadopelagic$renderArmorLeftArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
        renderArmorArm(matrices, vertexConsumers, light, player, false);
    }

    @Unique
    private void renderArmorArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, boolean rightArm) {
        for (FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRenderer : this.firstPersonArmFeatures) {
            if (featureRenderer instanceof RendersArmInFirstPerson<?> rendersArmInFirstPerson && rendersArmInFirstPerson.isFeatureEnabled(player)) {
                @SuppressWarnings("unchecked") BipedEntityModel<AbstractClientPlayerEntity> model = (BipedEntityModel<AbstractClientPlayerEntity>) rendersArmInFirstPerson.getModel(player);

                // Features don't render unless the player is rendered, so we are just forcing the rendering here but scaling it by zero to make it invisible
                matrices.push();
                matrices.scale(0,0,0);
                featureRenderer.render(matrices, vertexConsumers, light, player, 0, 0, 0, 0, 0, 0);
                matrices.pop();

                this.getModel().setVisible(true);
                this.setModelPose(player);
                model.handSwingProgress = 0.0F;
                model.sneaking = false;
                model.leaningPitch = 0.0F;
                model.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                ModelPart arm = rightArm ? rendersArmInFirstPerson.getRightArm(player) : rendersArmInFirstPerson.getLeftArm(player);
                arm.pitch = 0.0F;
                arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(rendersArmInFirstPerson.getTexture(player))), light, OverlayTexture.DEFAULT_UV);
            }
        }
    }
}
