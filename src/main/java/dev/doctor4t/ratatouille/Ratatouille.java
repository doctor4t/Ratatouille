package dev.doctor4t.ratatouille;

import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleItems;
import dev.doctor4t.ratatouille.index.RatatouilleSounds;
import dev.doctor4t.ratatouille.util.PlushOnHeadSupporterData;
import dev.upcraft.datasync.api.DataSyncAPI;
import dev.upcraft.datasync.api.SyncToken;
import dev.upcraft.datasync.api.util.Entitlements;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

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
    }

    public static boolean isSupporter(UUID uuid) {
        Optional<Entitlements> entitlements = Entitlements.token().get(uuid);
        return entitlements.map(value -> value.keys().stream().anyMatch(identifier -> identifier.equals(PLUSH_ON_HEAD_DATA))).orElse(false);
    }
}