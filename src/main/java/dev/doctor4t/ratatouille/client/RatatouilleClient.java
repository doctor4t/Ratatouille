package dev.doctor4t.ratatouille.client;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.gui.PlushOnHeadCosmeticsScreen;
import dev.doctor4t.ratatouille.client.model.armor.TestArmorModelDefinition;
import dev.doctor4t.ratatouille.client.render.entity.PlushBlockEntityRenderer;
import dev.doctor4t.ratatouille.client.util.ambience.AmbienceUtil;
import dev.doctor4t.ratatouille.client.util.ambience.BackgroundAmbience;
import dev.doctor4t.ratatouille.client.util.ambience.BlockEntityAmbience;
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
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypedActionResult;

public class RatatouilleClient implements ClientModInitializer {
    private static boolean openCosmeticsScreen;

    public static final AmbienceUtil AMBIENCE_MANAGER = new AmbienceUtil();

    @Override
    public void onInitializeClient() {
        // Register test features
        registerTestFeatures();

        // Initialize the render handler
//        RenderHandler.initialize();

        // Initialize ambience manager
        AMBIENCE_MANAGER.registerEvents();

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

    private static void registerTestFeatures() {
        // Custom armor model util
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

        // Ambience util
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            AmbienceUtil.registerBackgroundAmbience(new BackgroundAmbience(RatatouilleSounds.AMBIENT_SHIP, player -> player.getMainHandStack().isOf(RatatouilleBlocks.MAUVE_PLUSH.asItem()), 20));
            AmbienceUtil.registerBlockEntityAmbience(RatatouilleBlockEntities.PLUSH, new BlockEntityAmbience(SoundEvents.BLOCK_BEACON_AMBIENT, blockEntity -> blockEntity.getWorld().getBlockState(blockEntity.getPos().down()).isOf(Blocks.REDSTONE_BLOCK), 20));
        }
    }
}
