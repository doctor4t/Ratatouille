package dev.doctor4t.ratatouille.util.registrar;

import net.minecraft.registry.Registry;
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

    public void initialize() {
        TO_REGISTER.forEach((object, id) -> Registry.register(registry, id, object));
    }

}
