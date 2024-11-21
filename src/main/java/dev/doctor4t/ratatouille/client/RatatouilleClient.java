package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.render.PlayerHeadEntityRenderer;
import dev.doctor4t.ratatouille.client.render.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleEntities;
import dev.doctor4t.ratatouille.util.SupporterData;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.Items;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;

public class RatatouilleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Block special renders
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH
        );

        // Block entity renderers
        BlockEntityRendererFactories.register(RatatouilleBlockEntities.PLUSH, PlushBlockEntityRenderer::new);

        // Entity renderers
        EntityRendererRegistry.register(RatatouilleEntities.PLAYER_HEAD, PlayerHeadEntityRenderer::new);

        // Test supporter color
        UseItemCallback.EVENT.register((player, world, hand) -> {
            var stack = player.getStackInHand(hand);

            // when right clicking with a stick, switch to a new color
            // !!IMPORTANT: this is done clientside only!!
            if (stack.isOf(Items.STICK)) {
                if (world.isClient()) {
                    var random = player.getRandom();
                    String message = String.format("You have %d points!", random.nextInt(20000) + 300);
                    int color = MathHelper.packRgb(random.nextFloat(), random.nextFloat(), random.nextFloat());

                    SupporterData newData = new SupporterData(message, color);

                    // send the new values to the server
                    // (this returns a future so you can react to when the sending is finished)
                    Ratatouille.SUPPORTER_DATA_SYNC_TOKEN.setData(newData);
                }
                return TypedActionResult.success(stack);
            }
            return TypedActionResult.pass(stack);
        });
    }
}
