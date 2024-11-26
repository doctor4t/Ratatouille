package dev.doctor4t.ratatouille;

import dev.doctor4t.ratatouille.index.*;
import dev.doctor4t.ratatouille.util.PlushOnHeadSupporterData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ratatouille implements ModInitializer {
    public static final String MOD_ID = "ratatouille";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final SyncToken<PlushOnHeadSupporterData> PLUSH_ON_HEAD_DATA = DataSyncAPI.register(PlushOnHeadSupporterData.class, Ratatouille.id("plush_on_head"), PlushOnHeadSupporterData.CODEC);

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