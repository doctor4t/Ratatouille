package dev.doctor4t.ratatouille;

import dev.doctor4t.ratatouille.index.*;
import dev.doctor4t.ratatouille.util.SupporterPlushOnHeadData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ratatouille implements ModInitializer {
    public static final String MOD_ID = "ratatouille";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final SyncToken<SupporterPlushOnHeadData> PLUSH_ON_HEAD_DATA = DataSyncAPI.register(SupporterPlushOnHeadData.class, Ratatouille.id("plush_on_head"), SupporterPlushOnHeadData.CODEC);

    public static Identifier id(String string) {
        return new Identifier(MOD_ID, string);
    }

    @Override
    public void onInitialize() {
        RatatouilleBlocks.initialize();
        RatatouilleBlockEntities.initialize();
        RatatouilleItems.initialize();
        RatatouilleSounds.initialize();
        RatatouilleEntities.initialize();
    }
}