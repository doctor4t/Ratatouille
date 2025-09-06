package dev.doctor4t.ratatouille.util.registrar;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemRegistrar extends Registrar<Item> {
    public ItemRegistrar(String modId) {
        super(modId, Registries.ITEM);
    }

    public <T extends Item> T createDevExclusive(String name, T item) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            TO_REGISTER.put(item, Identifier.of(namespace, name));
            return item;
        } else {
            return null;
        }
    }
}
