package dev.doctor4t.ratatouille.client.render.feature;

import dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PlushOnHeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private final HeldItemRenderer heldItemRenderer;

    public PlushOnHeadFeatureRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(
            MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        if (livingEntity instanceof PlayerEntity player) {
            PlushOnHeadCosmetics.Plush plush = PlushOnHeadCosmetics.getPlush(player.getUuid());

            if (plush != PlushOnHeadCosmetics.Plush.NONE) {
                matrices.push();
                this.getContextModel().getHead().rotate(matrices);
                matrices.translate(0.0F, -0.25F, 0.0F);
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
                float scale = 0.625F;
                matrices.scale(scale, -scale, -scale);

                this.heldItemRenderer.renderItem(livingEntity, plush.item.getDefaultStack(), ModelTransformationMode.HEAD, false, matrices, vertexConsumerProvider, i);
                matrices.pop();
            }
        }
    }
}