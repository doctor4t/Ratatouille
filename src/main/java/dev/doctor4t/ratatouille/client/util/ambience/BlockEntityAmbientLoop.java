package dev.doctor4t.ratatouille.client.util.ambience;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class BlockEntityAmbientLoop extends MovingSoundInstance {
    private final BlockEntity blockEntity;
    private int transitionTimer;
    private final BlockEntityAmbience.PlayPredicate playPredicate;
    private final int fadeIn;
    private final int fadeOut;

    public BlockEntityAmbientLoop(BlockEntity blockEntity, SoundEvent soundEvent, SoundCategory soundCategory, BlockEntityAmbience.PlayPredicate playPredicate, int fadeIn, int fadeOut) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.blockEntity = blockEntity;

        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0f;
        BlockPos pos = blockEntity.getPos();
        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;

        this.playPredicate = playPredicate;
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }

    @Override
    public void tick() {
        if (!this.blockEntity.isRemoved() && this.transitionTimer >= 0) {
            int fadeTime;

            if (this.playPredicate.shouldPlay(this.blockEntity)) {
                ++this.transitionTimer;
                fadeTime = fadeIn;
            } else {
                --this.transitionTimer;
                fadeTime = fadeOut;
            }

            this.transitionTimer = Math.min(this.transitionTimer, fadeTime);
            this.volume = Math.max(0.0F, Math.min((float) this.transitionTimer / (float) fadeTime, 1.0F));
        }
    }
}