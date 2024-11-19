package dev.doctor4t.ratatouille.client.render;

import com.mojang.authlib.GameProfile;
import dev.doctor4t.ratatouille.entity.PlayerHeadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.Map;
import java.util.Random;

@Environment(value = EnvType.CLIENT)
public class PlayerHeadEntityRenderer extends EntityRenderer<PlayerHeadEntity> {
    private final ItemRenderer itemRenderer;
    private final Random random = new Random();
    private final Map<SkullBlock.SkullType, SkullBlockEntityModel> skullModels;
    private ItemEntity playerSkullEntity;

    public PlayerHeadEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.shadowRadius = 0.15f;
        this.shadowOpacity = 0.75f;
        this.skullModels = SkullBlockEntityRenderer.getModels(ctx.getModelLoader());
    }

    @Override
    public void render(PlayerHeadEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (playerSkullEntity == null) {
            ItemStack skullStack = new ItemStack(Items.SKELETON_SKULL);
//            skullStack.getOrCreateNbt().putString("SkullOwner", entity.getPlayerName());
            playerSkullEntity = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), skullStack);
        }

        float t;
        float s;
        matrices.push();
        ItemStack itemStack = playerSkullEntity.getStack();
        int j = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed(j);
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, playerSkullEntity.getWorld(), null, playerSkullEntity.getId());
        boolean bl = bakedModel.hasDepth();
        int k = 1;
        float h = 0.25f;
        float l = MathHelper.sin(((float) entity.age + tickDelta) / 10.0f + playerSkullEntity.uniqueOffset) * 0.1f + 0.1f;
        float m = bakedModel.getTransformation().getTransformation(ModelTransformationMode.GROUND).scale.y();
        matrices.translate(0.0, l + 0.25f * m, 0.0);
        float n = entity.age + tickDelta;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(n / 20f));
        float o = bakedModel.getTransformation().ground.scale.x();
        float p = bakedModel.getTransformation().ground.scale.y();
        float q = bakedModel.getTransformation().ground.scale.z();
        for (int u = 0; u < k; ++u) {
            matrices.push();
            //            this.itemRenderer.renderItem(itemStack, ModelTransformationMode.GROUND, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, bakedModel);
            GameProfile gameProfile2 = null;

            matrices.scale(0.75f, 0.75f, 0.75f);
            matrices.translate(-.5, 0, -.5);

            ItemStack stack = playerSkullEntity.getStack();
            Block block = ((BlockItem) stack.getItem()).getBlock();
//            if (stack.hasNbt()) {
//                NbtCompound nbtCompound = stack.getNbt();
//                if (nbtCompound.contains("SkullOwner", 10)) {
//                    gameProfile2 = NbtHelper.toGameProfile(nbtCompound.getCompound("SkullOwner"));
//                } else if (nbtCompound.contains("SkullOwner", 8) && !StringUtils.isBlank(nbtCompound.getString("SkullOwner"))) {
//                    gameProfile2 = new GameProfile(null, nbtCompound.getString("SkullOwner"));
//                    nbtCompound.remove("SkullOwner");
//                    SkullBlockEntity.loadProperties(gameProfile2, gameProfile -> nbtCompound.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), gameProfile)));
//                }
//            }
            SkullBlock.SkullType skullType = ((AbstractSkullBlock) block).getSkullType();
            SkullBlockEntityModel skullBlockEntityModel = this.skullModels.get(skullType);
            RenderLayer renderLayer = SkullBlockEntityRenderer.getRenderLayer(skullType, gameProfile2);
            SkullBlockEntityRenderer.renderSkull(null, 180.0f, 0.0f, matrices, vertexConsumers, light, skullBlockEntityModel, renderLayer);

            matrices.pop();
            if (bl) continue;
            matrices.translate(0.0f * o, 0.0f * p, 0.09375f * q);
        }
        matrices.pop();
        if (entity.hasCustomName()) {
            this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
//        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(PlayerHeadEntity entity) {
        return null;
    }
}

