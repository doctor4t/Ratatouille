package dev.doctor4t.ratatouille.client.render.feature;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.util.SupporterPlushOnHeadData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class PlushOnHeadFeatureRenderer<T extends LivingEntity, M extends EntityModel<T> & ModelWithHead> extends FeatureRenderer<T, M> {
    private final HeldItemRenderer heldItemRenderer;

    public PlushOnHeadFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(
            MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l
    ) {
        if (livingEntity instanceof PlayerEntity player) {
            this.getContextModel().getHead().rotate(matrices);

            // get the data for the player
            Optional<SupporterPlushOnHeadData> optional = player.datasync$get(Ratatouille.PLUSH_ON_HEAD_DATA);
            if (optional.isPresent()) {
                try {
                    Plush plush = Plush.fromName(optional.get().plushName());

                    float scale = 0.625F;
                    matrices.translate(0.0F, -0.25F, 0.0F);
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
                    matrices.scale(scale, -scale, -scale);

                    this.heldItemRenderer.renderItem(livingEntity, plush.itemStack, ModelTransformationMode.HEAD, false, matrices, vertexConsumerProvider, i);
                } catch (IllegalArgumentException ignored) {

                }
            }
        }
    }

    public enum Plush {
        RAT_MAID("rat_maid", new ItemStack(RatatouilleBlocks.RAT_MAID_PLUSH)),
        FOLLY("folly", new ItemStack(RatatouilleBlocks.FOLLY_PLUSH)),
        MAUVE("mauve", new ItemStack(RatatouilleBlocks.MAUVE_PLUSH));

        public final String name;
        public final ItemStack itemStack;

        Plush(String name, ItemStack itemStack) {
            this.name = name;
            this.itemStack = itemStack;
        }

        public static Plush fromName(String text) {
            for (Plush b : Plush.values()) {
                if (b.name.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}