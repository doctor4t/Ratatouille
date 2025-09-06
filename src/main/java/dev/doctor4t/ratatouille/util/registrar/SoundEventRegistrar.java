package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.registry.Registries;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventRegistrar extends Registrar<SoundEvent> {
    public SoundEventRegistrar(String namespace) {
        super(namespace, Registries.SOUND_EVENT);
    }

    public SoundEvent create(String name) {
        return super.create(name, SoundEvent.of(Identifier.of(namespace, name)));
    }

    public BlockSoundGroup createBlockSoundGroup(String name, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                create("block." + name + ".break"),
                create("block." + name + ".step"),
                create("block." + name + ".place"),
                create("block." + name + ".hit"),
                create("block." + name + ".fall"));
    }

    public BlockSoundGroup copyBlockSoundGroup(BlockSoundGroup blockSoundGroup, float volume, float pitch) {
        return new BlockSoundGroup(volume, pitch,
                blockSoundGroup.getBreakSound(),
                blockSoundGroup.getStepSound(),
                blockSoundGroup.getPlaceSound(),
                blockSoundGroup.getHitSound(),
                blockSoundGroup.getFallSound());
    }
}
