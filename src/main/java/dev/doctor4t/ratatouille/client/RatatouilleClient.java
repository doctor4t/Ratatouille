package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.client.render.PlayerHeadEntityRenderer;
import dev.doctor4t.ratatouille.client.render.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

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
    }
}
