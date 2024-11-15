package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;

import java.util.LinkedList;
import java.util.List;

public class RatatouilleSounds {

    protected static final List<SoundEvent> SOUND_EVENTS = new LinkedList<>();

    public static final SoundEvent BLOCK_RAT_MAID_PLUSH_HONK = create("block.rat_maid_plush.honk");
    public static final SoundEvent BLOCK_FOLLY_PLUSH_HONK = create("block.folly_plush.honk");
    public static final SoundEvent BLOCK_MAUVE_PLUSH_HONK = create("block.mauve_plush.honk");

    protected static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Ratatouille.id(name));
        SOUND_EVENTS.add(soundEvent);
        return soundEvent;
    }

    protected static BlockSoundGroup createBlockSoundGroup(String name, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                create("block." + name + ".break"),
                create("block." + name + ".step"),
                create("block." + name + ".place"),
                create("block." + name + ".hit"),
                create("block." + name + ".fall"));
    }

    protected static BlockSoundGroup copyBlockSoundGroup(BlockSoundGroup blockSoundGroup, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                blockSoundGroup.getBreakSound(),
                blockSoundGroup.getStepSound(),
                blockSoundGroup.getPlaceSound(),
                blockSoundGroup.getHitSound(),
                blockSoundGroup.getFallSound());
    }

    public static void initialize() {
        SOUND_EVENTS.forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent));
    }

}
