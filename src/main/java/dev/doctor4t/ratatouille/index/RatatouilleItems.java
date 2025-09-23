package dev.doctor4t.ratatouille.index;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.util.registrar.ItemRegistrar;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.*;

public interface RatatouilleItems {
    ItemRegistrar registrar = new ItemRegistrar(Ratatouille.MOD_ID);

    Item TEST_ARMOR_HELMET = !FabricLoader.getInstance().isDevelopmentEnvironment() ? null : registrar.create(
            "test_armor_helmet",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_CHESTPLATE = !FabricLoader.getInstance().isDevelopmentEnvironment() ? null : registrar.create(
            "test_armor_chestplate",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_LEGGINGS = !FabricLoader.getInstance().isDevelopmentEnvironment() ? null : registrar.create(
            "test_armor_leggings",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(37000)))
    );
    Item TEST_ARMOR_BOOTS = !FabricLoader.getInstance().isDevelopmentEnvironment() ? null : registrar.create(
            "test_armor_boots",
            new ArmorItem(ArmorMaterials.ARMADILLO, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(37000)))
    );

    static void initialize() {
        registrar.registerEntries();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(RatatouilleItems::addFunctionalEntries);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(RatatouilleItems::addCombatEntries);
    }

    static void addFunctionalEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        fabricItemGroupEntries.add(RatatouilleBlocks.RAT_MAID_PLUSH);
        fabricItemGroupEntries.add(RatatouilleBlocks.FOLLY_PLUSH);
        fabricItemGroupEntries.add(RatatouilleBlocks.MAUVE_PLUSH);
    }

    private static void addCombatEntries(FabricItemGroupEntries fabricItemGroupEntries) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            fabricItemGroupEntries.addAfter(Items.NETHERITE_BOOTS, TEST_ARMOR_HELMET);
            fabricItemGroupEntries.addAfter(TEST_ARMOR_HELMET, TEST_ARMOR_CHESTPLATE);
            fabricItemGroupEntries.addAfter(TEST_ARMOR_CHESTPLATE, TEST_ARMOR_LEGGINGS);
            fabricItemGroupEntries.addAfter(TEST_ARMOR_LEGGINGS, TEST_ARMOR_BOOTS);
        }
    }

}
