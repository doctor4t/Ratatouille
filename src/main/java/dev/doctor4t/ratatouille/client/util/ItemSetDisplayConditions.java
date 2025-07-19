package dev.doctor4t.ratatouille.client.util;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;

public class ItemSetDisplayConditions extends ArmorDisplayConditions {
    public final Item helmetItem;
    public final Item chesplateItem;
    public final Item leggingsItem;
    public final Item bootsItem;

    public ItemSetDisplayConditions(Item helmetItem, Item chesplateItem, Item leggingsItem, Item bootsItem) {
        this.helmetItem = helmetItem;
        this.chesplateItem = chesplateItem;
        this.leggingsItem = leggingsItem;
        this.bootsItem = bootsItem;
    }

    public boolean shouldDisplayHelmet(LivingEntity livingEntity) {
        return livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(this.helmetItem);
    }

    public boolean shouldDisplayChestplate(LivingEntity livingEntity) {
        return livingEntity.getEquippedStack(EquipmentSlot.CHEST).isOf(this.chesplateItem);
    }

    public boolean shouldDisplayLeggings(LivingEntity livingEntity) {
        return livingEntity.getEquippedStack(EquipmentSlot.LEGS).isOf(this.leggingsItem);
    }

    public boolean shouldDisplayBoots(LivingEntity livingEntity) {
        return livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(this.bootsItem);
    }
}
