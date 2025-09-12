package dev.doctor4t.ratatouille.util.registrar;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRegistrar extends Registrar<Item> {
    public ItemRegistrar(String modId) {
        super(modId, Registries.ITEM);
    }

    HashMap<RegistryKey<ItemGroup>, List<Item>> ITEM_GROUPS = new HashMap<>();

    public Item create(String name, Item item, RegistryKey<ItemGroup>... groups) {
        for (RegistryKey<ItemGroup> group : groups) {
            List<Item> itemList = ITEM_GROUPS.get(group);
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            itemList.add(item);
            ITEM_GROUPS.put(group, itemList);
        }

        return create(name, item);
    }

    @Override
    public void registerEntries() {
        super.registerEntries();

        ITEM_GROUPS.forEach((group, items) -> {
            ItemGroupEvents.modifyEntriesEvent(group).register(entries -> {
                for (Item item : items) {
                    entries.add(new ItemStack(item));
                }
            });
        });
    }
}
