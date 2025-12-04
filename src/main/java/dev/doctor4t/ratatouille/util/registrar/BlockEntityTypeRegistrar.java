package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class BlockEntityTypeRegistrar extends Registrar<BlockEntityType<?>> {
    public BlockEntityTypeRegistrar(String namespace) {
        super(namespace, Registries.BLOCK_ENTITY_TYPE);
    }

    public <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityFactory<? extends T> factory, Block... blocks) {
        return (BlockEntityType<T>) super.create(name, new BlockEntityType(factory, Set.of(blocks)));
    }

    @FunctionalInterface
    public interface BlockEntityFactory<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}
