package dev.doctor4t.ratatouille.client.util;
import dev.doctor4t.ratatouille.client.model.armor.CustomArmorModelDefinition;
import dev.doctor4t.ratatouille.client.model.armor.CustomBipedArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class CustomModelArmorUtil {
    public static final Map<SetItems, SetRenderData> CUSTOM_ARMOR_MODELS = new HashMap<>();

    public static @NotNull EntityModelLayer registerModelLayerMain(Identifier identifier) {
        return new EntityModelLayer(identifier, "main");
    }

    public static void registerCustomArmor(Identifier id, SetItems setItems, CustomArmorModelDefinition armorModelDefinition, int textureWidth, int textureHeight) {
        EntityModelLayer modelLayer = registerModelLayerMain(id);

        TexturedModelData modelData = TexturedModelData.of(CustomBipedArmorModel.getModelData(armorModelDefinition::addModelParts, Dilation.NONE), textureWidth, textureHeight);

        SetRenderData setRenderData = new SetRenderData(
                modelLayer,
                modelData,
                context -> new CustomBipedArmorModel<>(context.getPart(modelLayer), armorModelDefinition)
        );

        CUSTOM_ARMOR_MODELS.put(setItems, setRenderData);

        EntityModelLayerRegistry.registerModelLayer(setRenderData.modelLayer(), setRenderData::modelData);
    }

    public record SetItems(
            Item helmetItem,
            Item chesplateItem,
            Item leggingsItem,
            Item bootsItem) {

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

    public record SetRenderData(
            EntityModelLayer modelLayer,
            TexturedModelData modelData,
            Function<EntityRendererFactory.Context, CustomBipedArmorModel<LivingEntity>> modelConstructor) {
    }
}
