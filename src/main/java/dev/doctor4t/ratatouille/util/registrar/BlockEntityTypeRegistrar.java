package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BlockEntityTypeRegistrar extends Registrar<BlockEntityType<?>> {
    public BlockEntityTypeRegistrar(String namespace) {
        super(namespace, Registries.BLOCK_ENTITY_TYPE);
    }
}
