package dev.doctor4t.ratatouille.util.registrar;

import dev.doctor4t.ratatouille.util.TextUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

public class EntityTypeRegistrar extends Registrar<EntityType<?>> {
    public EntityTypeRegistrar(String namespace) {
        super(namespace, Registries.ENTITY_TYPE);
    }

    public <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> blockEntityBuilder) {
        return (EntityType<T>) super.create(name, blockEntityBuilder.build());
    }

    @Override
    public void generateLang(RegistryWrapper.WrapperLookup wrapperLookup, FabricLanguageProvider.TranslationBuilder builder) {
        TO_REGISTER.forEach((t, identifier) -> builder.add(t, TextUtils.formatValueString(identifier.getPath())));
    }
}
