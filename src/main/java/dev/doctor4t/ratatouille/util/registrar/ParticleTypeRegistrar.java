package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;

public class ParticleTypeRegistrar extends Registrar<ParticleType<?>> {
    public ParticleTypeRegistrar(String namespace) {
        super(namespace, Registries.PARTICLE_TYPE);
    }
}
