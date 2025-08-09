package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public interface RatatouilleItems {
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item TEST_ARMOR_HELMET = createDevExclusive(
            "test_armor_helmet",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_CHESTPLATE = createDevExclusive(
            "test_armor_chestplate",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_LEGGINGS = createDevExclusive(
            "test_armor_leggings",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_BOOTS = createDevExclusive(
            "test_armor_boots",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(37000)))
    );

    static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, Ratatouille.id(name));

        return item;
    }

    static <T extends Item> T createDevExclusive(String name, T item) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ITEMS.put(item, Ratatouille.id(name));
            return item;
        } else {
            return null;
        }
    }

    static void initialize() {
        ITEMS.forEach((item, id) -> Registry.register(Registries.ITEM, id, item));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(RatatouilleItems::addFunctionalEntries);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(RatatouilleItems::addCombatEntries);
    }

    static void addFunctionalEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.add(RatatouilleBlocks.RAT_MAID_PLUSH);
        fabricItemGroupEntries.add(RatatouilleBlocks.FOLLY_PLUSH);
        fabricItemGroupEntries.add(RatatouilleBlocks.MAUVE_PLUSH);
    }

    private static void addCombatEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.addAfter(Items.NETHERITE_BOOTS, TEST_ARMOR_HELMET);
        fabricItemGroupEntries.addAfter(TEST_ARMOR_HELMET, TEST_ARMOR_CHESTPLATE);
        fabricItemGroupEntries.addAfter(TEST_ARMOR_CHESTPLATE, TEST_ARMOR_LEGGINGS);
        fabricItemGroupEntries.addAfter(TEST_ARMOR_LEGGINGS, TEST_ARMOR_BOOTS);
    }

}
