package dev.doctor4t.ratatouille;

import dev.doctor4t.ratatouille.index.RatatouilleBlockEntities;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import dev.doctor4t.ratatouille.index.RatatouilleItems;
import dev.doctor4t.ratatouille.index.RatatouilleSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ratatouille implements ModInitializer {
    public static final String MOD_ID = "ratatouille";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
}