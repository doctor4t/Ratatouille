package dev.doctor4t.ratatouille.client.util.armor;

import net.minecraft.entity.LivingEntity;

public abstract class ArmorDisplayConditions {
    public abstract boolean shouldDisplayHelmet(LivingEntity livingEntity);

    public abstract boolean shouldDisplayHelmetGlint(LivingEntity livingEntity);

    public abstract boolean shouldDisplayChestplate(LivingEntity livingEntity);

    public abstract boolean shouldDisplayChestplateGlint(LivingEntity livingEntity);

    public abstract boolean shouldDisplayLeggings(LivingEntity livingEntity);

    public abstract boolean shouldDisplayLeggingsGlint(LivingEntity livingEntity);

    public abstract boolean shouldDisplayBoots(LivingEntity livingEntity);

    public abstract boolean shouldDisplayBootsGlint(LivingEntity livingEntity);
}
