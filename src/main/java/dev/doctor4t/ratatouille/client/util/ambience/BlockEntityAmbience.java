package dev.doctor4t.ratatouille.client.util.ambience;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class BlockEntityAmbience {
    // Exclusive ambiences are iterated through in order, wherein the first with a true predicate is selected.
    final SoundFactory factory;
    @Nullable
    private MovingSoundInstance soundInstance;

    public BlockEntityAmbience(SoundEvent soundEvent, float volume, PlayPredicate predicate, int fadeTime) {
        this(soundEvent, SoundCategory.BLOCKS, volume, predicate, fadeTime, fadeTime);
    }

    BlockEntityAmbience(SoundEvent soundEvent, SoundCategory soundCategory, float volume, PlayPredicate predicate, int fadeIn, int fadeOut) {
        this.factory = blockEntity -> new BlockEntityAmbientLoop(blockEntity, soundEvent, soundCategory, volume, predicate, fadeIn, fadeOut);
    }

    @FunctionalInterface
    public interface PlayPredicate {
        boolean shouldPlay(BlockEntity blockEntity);
    }

    @FunctionalInterface
    public interface SoundFactory {
        MovingSoundInstance create(BlockEntity blockEntity);
    }

}
