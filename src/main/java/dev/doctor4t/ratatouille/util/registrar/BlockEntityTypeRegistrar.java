package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;

public class BlockEntityTypeRegistrar extends Registrar<BlockEntityType<?>> {
    public BlockEntityTypeRegistrar(String namespace) {
        super(namespace, Registries.BLOCK_ENTITY_TYPE);
    }

    public <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType.Builder<T> blockEntityBuilder) {
        return (BlockEntityType<T>) super.create(name, blockEntityBuilder.build());
    }
}
