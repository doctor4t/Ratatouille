package dev.doctor4t.ratatouille.client.util;

import net.minecraft.entity.LivingEntity;

public abstract class ArmorDisplayConditions {
     public abstract boolean shouldDisplayHelmet(LivingEntity livingEntity);

     public abstract boolean shouldDisplayChestplate(LivingEntity livingEntity);

     public abstract boolean shouldDisplayLeggings(LivingEntity livingEntity);

     public abstract boolean shouldDisplayBoots(LivingEntity livingEntity);
}
