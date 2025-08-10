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

    public BlockEntityAmbience(SoundEvent soundEvent, PlayPredicate predicate, int fadeTime) {
        this(soundEvent, SoundCategory.BLOCKS, predicate, fadeTime, fadeTime);
    }

    BlockEntityAmbience(SoundEvent soundEvent, SoundCategory soundCategory, PlayPredicate predicate, int fadeIn, int fadeOut) {
        this.factory = blockEntity -> new BlockEntityAmbientLoop(blockEntity, soundEvent, soundCategory, predicate, fadeIn, fadeOut);
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
