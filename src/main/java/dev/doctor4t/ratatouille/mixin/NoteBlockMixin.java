package dev.doctor4t.ratatouille.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.ratatouille.block.PlushBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {
    @WrapOperation(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/enums/NoteBlockInstrument;getSound()Lnet/minecraft/registry/entry/RegistryEntry;"))
    private RegistryEntry<SoundEvent> ratatouille$plushieReplaceNoteBlockSound(NoteBlockInstrument instance, Operation<RegistryEntry<SoundEvent>> original, BlockState state, World world, BlockPos pos) {
        if (world.getBlockState(pos.down()).getBlock() instanceof PlushBlock)
            return RegistryEntry.of(PlushBlock.getSound(world.getBlockState(pos.down())));
        return original.call(instance);
    }
}