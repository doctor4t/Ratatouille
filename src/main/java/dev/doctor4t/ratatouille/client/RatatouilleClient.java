package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.client.gui.PlushOnHeadCosmeticsScreen;
import dev.doctor4t.ratatouille.client.lib.render.handlers.RenderHandler;
import dev.doctor4t.ratatouille.client.render.entity.PlayerHeadEntityRenderer;
import dev.doctor4t.ratatouille.client.render.entity.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class RatatouilleClient implements ClientModInitializer {
    private static boolean openCosmeticsScreen;

    @Override
    public void onInitializeClient() {
        // Initialize the render handler
        RenderHandler.initialize();

        // Block special renders
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH
        );

        // Block entity renderers
        BlockEntityRendererFactories.register(RatatouilleBlockEntities.PLUSH, PlushBlockEntityRenderer::new);

        // Entity renderers
        EntityRendererRegistry.register(RatatouilleEntities.PLAYER_HEAD, PlayerHeadEntityRenderer::new);

        // open the plush on head supporter cosmetics settings
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(RatatouilleBlocks.RAT_MAID_PLUSH.asItem()) || stack.isOf(RatatouilleBlocks.FOLLY_PLUSH.asItem()) || stack.isOf(RatatouilleBlocks.MAUVE_PLUSH.asItem())) && player.isSneaking()) {
                openCosmeticsScreen = true;
            }

            return TypedActionResult.pass(stack);
        });

        // open cosmetics screen on render thread
        WorldRenderEvents.LAST.register(context -> {
            if (openCosmeticsScreen) {
                minecraftClient.setScreen(new PlushOnHeadCosmeticsScreen());
                openCosmeticsScreen = false;
            }
        });

    }
}
