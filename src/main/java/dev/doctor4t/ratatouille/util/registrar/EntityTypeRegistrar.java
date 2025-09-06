package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;

public class EntityTypeRegistrar extends Registrar<EntityType<?>> {
    public EntityTypeRegistrar(String namespace) {
        super(namespace, Registries.ENTITY_TYPE);
    }
}
