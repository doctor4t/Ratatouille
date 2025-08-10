package dev.doctor4t.ratatouille.client.util.ambience;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;

import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class AmbienceUtil implements
        ClientBlockEntityEvents.Load,
        ClientBlockEntityEvents.Unload,
        ClientTickEvents.StartWorldTick,
        ClientTickEvents.EndTick,
        ClientPlayConnectionEvents.Disconnect {

    private static final List<BackgroundAmbience> BACKGROUND_AMBIENCES = new ObjectArrayList<>();
    private static final HashMap<BlockEntityType<? extends BlockEntity>, BlockEntityAmbience> BLOCK_ENTITY_AMBIENCES = new HashMap<>();

	public static void registerBackgroundAmbience(BackgroundAmbience backgroundAmbience) {
		BACKGROUND_AMBIENCES.add(backgroundAmbience);
	}

	public static void registerBlockEntityAmbience(BlockEntityType<? extends BlockEntity> blockEntityType, BlockEntityAmbience blockEntityAmbience) {
        BLOCK_ENTITY_AMBIENCES.put(blockEntityType, blockEntityAmbience);
	}

    public void registerEvents() {
        ClientBlockEntityEvents.BLOCK_ENTITY_LOAD.register(this);
        ClientBlockEntityEvents.BLOCK_ENTITY_UNLOAD.register(this);
        ClientTickEvents.START_WORLD_TICK.register(this);
        ClientTickEvents.END_CLIENT_TICK.register(this);
        ClientPlayConnectionEvents.DISCONNECT.register(this);
    }

    @Override
    public void onStartTick(ClientWorld world) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return;
        }

        SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();
        for (BackgroundAmbience backgroundAmbience : BACKGROUND_AMBIENCES) {
            backgroundAmbience.tryStarting(player, soundManager);
        }
    }

    @Override
    public void onLoad(BlockEntity blockEntity, ClientWorld world) {
        SoundManager soundManager = MinecraftClient.getInstance().getSoundManager();

        BlockEntityAmbience ambience = BLOCK_ENTITY_AMBIENCES.get(blockEntity.getType());
        if (ambience != null) {
            soundManager.playNextTick(ambience.factory.create(blockEntity));
        }
    }

    @Override
    public void onUnload(BlockEntity entity, ClientWorld world) {
    }

    @Override
    public void onEndTick(MinecraftClient client) {
    }

    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
    }
}
