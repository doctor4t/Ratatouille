package dev.doctor4t.ratatouille.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class RecoveryPosComponent implements AutoSyncedComponent {
    private final PlayerEntity player;
    private BlockPos deathPos = null;
    private int deathTime = 0;

    public RecoveryPosComponent(PlayerEntity player) {
        this.player = player;
    }

    public void tick() {
        if (this.deathTime > 0) {
            this.deathTime--;
            if (this.deathTime == 0) {
                this.setDeathPos(null);
            }
        }
    }

    public Optional<BlockPos> getDeathPos() {
        return Optional.ofNullable(this.deathPos);
    }

    public void setDeathPos(BlockPos deathPos) {
        this.deathPos = deathPos;
        if (deathPos != null) this.deathTime = 100;
        RatatouilleComponents.RECOVERY_POS_COMPONENT.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        if (nbtCompound.contains("deathPos")) {
            NbtCompound pos = nbtCompound.getCompound("deathPos");
            this.deathPos = new BlockPos(pos.getInt("x"), pos.getInt("y"), pos.getInt("z"));
        } else {
            this.deathPos = null;
        }

    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        if (this.deathPos != null) {
            NbtCompound pos = new NbtCompound();
            pos.putInt("x", this.deathPos.getX());
            pos.putInt("y", this.deathPos.getY());
            pos.putInt("z", this.deathPos.getZ());
            nbtCompound.put("deathPos", pos);
        }

    }
}