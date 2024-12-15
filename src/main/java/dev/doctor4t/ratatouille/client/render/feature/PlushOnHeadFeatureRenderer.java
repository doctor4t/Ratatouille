package dev.doctor4t.ratatouille.client.render.feature;

import dev.doctor4t.ratatouille.client.render.state.PlushOnHeadRenderStateAddition;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PlushOnHeadFeatureRenderer<S extends PlayerEntityRenderState, M extends EntityModel<S> & ModelWithHead> extends FeatureRenderer<S, M>  {
    public PlushOnHeadFeatureRenderer(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
        ItemRenderState plushState = ((PlushOnHeadRenderStateAddition) state).ratatouille$getPlushOnHeadRenderState();
        if (!plushState.isEmpty()) {
            matrices.push();
            this.getContextModel().getHead().rotate(matrices);
            matrices.translate(0.0F, -0.25F, 0.0F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            float scale = 0.625F;
            matrices.scale(scale, -scale, -scale);

            plushState.render(matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);

            matrices.pop();
        }
    }
}