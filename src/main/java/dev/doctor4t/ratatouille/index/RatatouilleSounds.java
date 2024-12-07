package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;

import java.util.LinkedList;
import java.util.List;

public interface RatatouilleSounds {

    List<SoundEvent> SOUND_EVENTS = new LinkedList<>();

    SoundEvent BLOCK_RAT_MAID_PLUSH_HONK = create("block.rat_maid_plush.honk");
    SoundEvent BLOCK_FOLLY_PLUSH_HONK = create("block.folly_plush.honk");
    SoundEvent BLOCK_MAUVE_PLUSH_HONK = create("block.mauve_plush.honk");

    static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Ratatouille.id(name));
        SOUND_EVENTS.add(soundEvent);
        return soundEvent;
    }

    static BlockSoundGroup createBlockSoundGroup(String name, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                create("block." + name + ".break"),
                create("block." + name + ".step"),
                create("block." + name + ".place"),
                create("block." + name + ".hit"),
                create("block." + name + ".fall"));
    }

    static BlockSoundGroup copyBlockSoundGroup(BlockSoundGroup blockSoundGroup, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                blockSoundGroup.getBreakSound(),
                blockSoundGroup.getStepSound(),
                blockSoundGroup.getPlaceSound(),
                blockSoundGroup.getHitSound(),
                blockSoundGroup.getFallSound());
    }

    static void initialize() {
        SOUND_EVENTS.forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent));
    }

}
