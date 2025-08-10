package dev.doctor4t.ratatouille.client.util.ambience;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class BackgroundAmbientLoop extends MovingSoundInstance {
    private final ClientPlayerEntity player;
    private int transitionTimer;
    private final BackgroundAmbience.PlayPredicate playPredicate;
    private final int fadeIn;
    private final int fadeOut;

    public BackgroundAmbientLoop(ClientPlayerEntity player, SoundEvent soundEvent, SoundCategory soundCategory, BackgroundAmbience.PlayPredicate playPredicate, int fadeIn, int fadeOut) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.player = player;

        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 1.0F;
        this.relative = true;

        this.playPredicate = playPredicate;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    @Override
    public void tick() {
        if (!this.player.isRemoved() && this.transitionTimer >= 0) {
            int fadeTime;

            if (this.playPredicate.shouldPlay(this.player)) {
                ++this.transitionTimer;
                fadeTime = fadeIn;
            } else {
                --this.transitionTimer;
                fadeTime = fadeOut;
            }

            this.transitionTimer = Math.min(this.transitionTimer, fadeTime);
            this.volume = Math.max(0.0F, Math.min((float) this.transitionTimer / (float) fadeTime, 1.0F));
        } else {
            this.setDone();
        }
    }
}