package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RatatouilleItems {
    protected static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    protected static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, Ratatouille.id(name));

        return item;
    }

    public static void initialize() {
        ITEMS.forEach((item, id) -> Registry.register(Registries.ITEM, id, item));

        Map<Item, Consumer<FabricItemGroupEntries>> stackAppenders = new HashMap<>();
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> addItemGroupEntries(entries, stackAppenders));
    }

    private static void addItemGroupEntries(FabricItemGroupEntries entries, Map<Item, Consumer<FabricItemGroupEntries>> stackAppenders) {
        ITEMS.keySet().forEach(item -> stackAppenders.getOrDefault(item, itemGroupEntries -> itemGroupEntries.add(item)).accept(entries));
    }
}
