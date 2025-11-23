package dev.doctor4t.ratatouille.util.registrar;

import dev.doctor4t.ratatouille.util.TextUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Registrar<T> {
    final String namespace;
    final Registry<T> registry;

    final Map<T, Identifier> TO_REGISTER = new LinkedHashMap<>();

    public Registrar(String namespace, Registry<T> registry) {
        this.namespace = namespace;
        this.registry = registry;
    }

    public <M extends T> T create(String name, M object) {
        TO_REGISTER.put(object, Identifier.of(namespace, name));
        return object;
    }

    public void registerEntries() {
        TO_REGISTER.forEach((object, id) -> Registry.register(registry, id, object));
    }

    public Map<T, Identifier> getEntriesToRegister() {
        return TO_REGISTER;
    }

    public void generateLang(RegistryWrapper.WrapperLookup wrapperLookup, FabricLanguageProvider.TranslationBuilder builder) {
        TO_REGISTER.forEach((t, identifier) -> {
            builder.add(identifier, TextUtils.formatValueString(identifier.getPath()));
        });
    }
}
