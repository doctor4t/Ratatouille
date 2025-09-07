package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.util.registrar.SoundEventRegistrar;
import net.minecraft.sound.SoundEvent;

public interface RatatouilleSounds {
    SoundEventRegistrar index = new SoundEventRegistrar(Ratatouille.MOD_ID);

    SoundEvent BLOCK_RAT_MAID_PLUSH_HONK = index.create("block.rat_maid_plush.honk");
    SoundEvent BLOCK_FOLLY_PLUSH_HONK = index.create("block.folly_plush.honk");
    SoundEvent BLOCK_MAUVE_PLUSH_HONK = index.create("block.mauve_plush.honk");

    SoundEvent AMBIENT_SHIP = index.create("ambient.ship");

    static void initialize() {
        index.registerEntries();
    }
}
