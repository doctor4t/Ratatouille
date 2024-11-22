package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.render.PlayerHeadEntityRenderer;
import dev.doctor4t.ratatouille.client.render.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleEntities;
import dev.doctor4t.ratatouille.util.SupporterPlushOnHeadData;
import dev.upcraft.datasync.api.DataSyncAPI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.TypedActionResult;

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

        // Test supporter plush head setting
        UseItemCallback.EVENT.register((player, world, hand) -> {
            var stack = player.getStackInHand(hand);

            // change plush on head when shift using a plushie
            if (stack.isOf(RatatouilleBlocks.RAT_MAID_PLUSH.asItem())) {
                SupporterPlushOnHeadData newData = new SupporterPlushOnHeadData("rat_maid");
                Ratatouille.PLUSH_ON_HEAD_DATA.setData(newData);
                return TypedActionResult.success(stack);
            }
            if (stack.isOf(RatatouilleBlocks.FOLLY_PLUSH.asItem())) {
                SupporterPlushOnHeadData newData = new SupporterPlushOnHeadData("folly");
                Ratatouille.PLUSH_ON_HEAD_DATA.setData(newData);
                return TypedActionResult.success(stack);
            }
            if (stack.isOf(RatatouilleBlocks.MAUVE_PLUSH.asItem())) {
                SupporterPlushOnHeadData newData = new SupporterPlushOnHeadData("mauve");
                Ratatouille.PLUSH_ON_HEAD_DATA.setData(newData);
                return TypedActionResult.success(stack);
            }

            return TypedActionResult.pass(stack);
        });
    }
}
