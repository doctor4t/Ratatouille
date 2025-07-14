package dev.doctor4t.ratatouille.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;

public class CustomRenderArmorItem extends ArmorItem implements CustomRenderArmor {
    public CustomRenderArmorItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);
    }
}
