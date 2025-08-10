package dev.doctor4t.ratatouille.client.util.armor;
import dev.doctor4t.ratatouille.client.model.armor.CustomArmorModelDefinition;
import dev.doctor4t.ratatouille.client.model.armor.CustomBipedArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CustomModelArmorUtil {
    public static final Map<ArmorDisplayConditions, SetRenderData> CUSTOM_ARMOR_MODELS = new HashMap<>();

    public static final Set<Item> CUSTOM_ARMOR_ITEMS = new HashSet<>();

    public static @NotNull EntityModelLayer registerModelLayerMain(Identifier identifier) {
        return new EntityModelLayer(identifier, "main");
    }

    public static void registerCustomArmor(Identifier id, ArmorDisplayConditions displayConditions, CustomArmorModelDefinition armorModelDefinition, int textureWidth, int textureHeight) {
        if (displayConditions instanceof ItemSetDisplayConditions itemSetDisplayConditions) {
            CUSTOM_ARMOR_ITEMS.add(itemSetDisplayConditions.helmetItem);
            CUSTOM_ARMOR_ITEMS.add(itemSetDisplayConditions.chesplateItem);
            CUSTOM_ARMOR_ITEMS.add(itemSetDisplayConditions.leggingsItem);
            CUSTOM_ARMOR_ITEMS.add(itemSetDisplayConditions.bootsItem);
        }

        EntityModelLayer modelLayer = registerModelLayerMain(id);

        TexturedModelData modelData = TexturedModelData.of(CustomBipedArmorModel.getModelData(armorModelDefinition::addModelParts, Dilation.NONE), textureWidth, textureHeight);

        SetRenderData setRenderData = new SetRenderData(
                modelLayer,
                modelData,
                context -> new CustomBipedArmorModel<>(context.getPart(modelLayer), armorModelDefinition)
        );

        CUSTOM_ARMOR_MODELS.put(displayConditions, setRenderData);

        EntityModelLayerRegistry.registerModelLayer(setRenderData.modelLayer(), setRenderData::modelData);
    }

    public record SetRenderData(
            EntityModelLayer modelLayer,
            TexturedModelData modelData,
            Function<EntityRendererFactory.Context, CustomBipedArmorModel<LivingEntity>> modelConstructor) {
    }
}
