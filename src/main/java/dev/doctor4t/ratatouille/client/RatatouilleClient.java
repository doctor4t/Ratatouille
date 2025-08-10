package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.gui.PlushOnHeadCosmeticsScreen;
import dev.doctor4t.ratatouille.client.model.armor.TestArmorModelDefinition;
import dev.doctor4t.ratatouille.client.render.entity.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.client.util.ambience.Ambience;
import dev.doctor4t.ratatouille.client.util.ambience.AmbienceManager;
import dev.doctor4t.ratatouille.client.util.armor.CustomModelArmorUtil;
import dev.doctor4t.ratatouille.client.util.armor.ItemSetDisplayConditions;
import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleItems;
import dev.doctor4t.ratatouille.index.RatatouilleSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class RatatouilleClient implements ClientModInitializer {
    private static boolean openCosmeticsScreen;

    public static final AmbienceManager AMBIENCE_MANAGER = new AmbienceManager();

    @Override
    public void onInitializeClient() {
        // Initialize the render handler
//        RenderHandler.initialize();

        // Initialize ambience manager
        AMBIENCE_MANAGER.registerEvents();

        AmbienceManager.registerAmbience(new Ambience(RatatouilleSounds.AMBIENT_SHIP, player -> FabricLoader.getInstance().isDevelopmentEnvironment() && player.getMainHandStack().isOf(RatatouilleBlocks.MAUVE_PLUSH.asItem()), 20));

        // Register test custom armor
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            CustomModelArmorUtil.registerCustomArmor(
                    Ratatouille.id("test_armor"),
                    new ItemSetDisplayConditions(
                            RatatouilleItems.TEST_ARMOR_HELMET,
                            RatatouilleItems.TEST_ARMOR_CHESTPLATE,
                            RatatouilleItems.TEST_ARMOR_LEGGINGS,
                            RatatouilleItems.TEST_ARMOR_BOOTS
                    ),
                    new TestArmorModelDefinition(),
                    128, 128
            );
        }

        // Block special renders
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                RatatouilleBlocks.RAT_MAID_PLUSH, RatatouilleBlocks.FOLLY_PLUSH, RatatouilleBlocks.MAUVE_PLUSH
        );

        // Block entity renderers
        BlockEntityRendererFactories.register(RatatouilleBlockEntities.PLUSH, PlushBlockEntityRenderer::new);

        // Open the plush on head supporter cosmetics settings
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if ((stack.isOf(RatatouilleBlocks.RAT_MAID_PLUSH.asItem()) || stack.isOf(RatatouilleBlocks.FOLLY_PLUSH.asItem()) || stack.isOf(RatatouilleBlocks.MAUVE_PLUSH.asItem())) && player.isSneaking()) {
                openCosmeticsScreen = true;
            }

            return TypedActionResult.pass(stack);
        });

        // Open cosmetics screen on render thread
        WorldRenderEvents.LAST.register(context -> {
            if (openCosmeticsScreen) {
                minecraftClient.setScreen(new PlushOnHeadCosmeticsScreen());
                openCosmeticsScreen = false;
            }
        });

    }
}
