package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.client.render.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class RatatouilleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH
        );

        BlockEntityRendererFactories.register(RatatouilleBlockEntities.PLUSH, PlushBlockEntityRenderer::new);
    }
}
