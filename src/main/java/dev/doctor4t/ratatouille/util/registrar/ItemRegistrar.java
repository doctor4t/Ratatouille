package dev.doctor4t.ratatouille.util.registrar;

import dev.doctor4t.ratatouille.util.TextUtils;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;

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

    @Override
    public void generateLang(RegistryWrapper.WrapperLookup wrapperLookup, FabricLanguageProvider.TranslationBuilder builder) {
        TO_REGISTER.forEach((t, identifier) -> builder.add(t, TextUtils.formatValueString(identifier.getPath())));
    }
}
