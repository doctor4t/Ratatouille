package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

public class EntityTypeRegistrar extends Registrar<EntityType<?>> {
    public EntityTypeRegistrar(String namespace) {
        super(namespace, Registries.ENTITY_TYPE);
    }

    public <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> blockEntityBuilder) {
        return (EntityType<T>) super.create(name, blockEntityBuilder.build());
    }
}
