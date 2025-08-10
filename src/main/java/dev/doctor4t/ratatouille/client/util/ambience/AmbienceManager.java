package dev.doctor4t.ratatouille.client.util.ambience;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientBlockEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.world.ClientWorld;

import java.util.List;

@Environment(EnvType.CLIENT)
public class AmbienceManager implements
        ClientBlockEntityEvents.Load,
        ClientBlockEntityEvents.Unload,
        ClientTickEvents.StartWorldTick,
        ClientTickEvents.EndTick,
        ClientPlayConnectionEvents.Disconnect {

    private static final List<Ambience> AMBIENCES = new ObjectArrayList<>();

	public static void registerAmbience(Ambience ambience) {
		AMBIENCES.add(ambience);
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
        for (Ambience ambience : AMBIENCES) {
            ambience.tryStarting(player, soundManager);
        }
    }

    @Override
    public void onLoad(BlockEntity entity, ClientWorld world) {
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
