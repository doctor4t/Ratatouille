package dev.doctor4t.ratatouille.components;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;

public class RatatouilleComponents implements EntityComponentInitializer {
    public static final ComponentKey<RecoveryPosComponent> RECOVERY_POS_COMPONENT = ComponentRegistry.getOrCreate(Ratatouille.id("recoverypos"), RecoveryPosComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, RECOVERY_POS_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(RecoveryPosComponent::new);
    }
}