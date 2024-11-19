package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.entity.PlayerHeadEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class RatatouilleEntities {
    public static EntityType<PlayerHeadEntity> PLAYER_HEAD;

    public static void initialize() {
        PLAYER_HEAD = registerEntity("player_head", FabricEntityTypeBuilder.<PlayerHeadEntity>create(SpawnGroup.MISC, PlayerHeadEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    }

    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, Ratatouille.id(name), entityType);
    }
}
