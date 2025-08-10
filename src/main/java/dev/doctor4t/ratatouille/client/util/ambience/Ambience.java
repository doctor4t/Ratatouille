package dev.doctor4t.ratatouille.client.util.ambience;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

public class Ambience {
    // Exclusive ambiences are iterated through in order, wherein the first with a true predicate is selected.
    private final PlayPredicate predicate;
    private final SoundFactory factory;
    @Nullable
    private SoundInstance soundInstance;

    public Ambience(SoundEvent soundEvent, PlayPredicate predicate, int fadeTime) {
        this(soundEvent, SoundCategory.AMBIENT, predicate, fadeTime, fadeTime);
    }

    Ambience(SoundEvent soundEvent, SoundCategory soundCategory, PlayPredicate predicate, int fadeIn, int fadeOut) {
        this.factory = player -> new MasterAmbientLoop(player, soundEvent, soundCategory, predicate, fadeIn, fadeOut);
        this.predicate = predicate;
    }

    public boolean tryStarting(ClientPlayerEntity player, SoundManager soundManager) {
        if (this.soundInstance == null || (this.predicate.shouldPlay(player) && !soundManager.isPlaying(this.soundInstance))) {
            this.soundInstance = this.factory.create(player);
            soundManager.play(this.soundInstance);
            return true;
        }
        return false;
    }

    @FunctionalInterface
    public interface PlayPredicate {
        boolean shouldPlay(ClientPlayerEntity player);
    }

    @FunctionalInterface
    public interface SoundFactory {
        SoundInstance create(ClientPlayerEntity player);
    }

}
