package dev.doctor4t.ratatouille.client.model.armor;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CustomBipedArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {
    private final CustomArmorModelDefinition modelDefinition;

    public final ModelPart helmet;
    public final ModelPart body_chestplate;
    public final ModelPart body_leggings;
    public final ModelPart right_arm_chestplate;
    public final ModelPart left_arm_chestplate;
    public final ModelPart right_leg_leggings;
    public final ModelPart right_leg_boot;
    public final ModelPart left_leg_leggings;
    public final ModelPart left_leg_boot;

    public CustomBipedArmorModel(ModelPart root, CustomArmorModelDefinition modelDefinition) {
        super(root);

        this.modelDefinition = modelDefinition;

        this.helmet = this.head.getChild("helmet");

        this.body_leggings = this.body.getChild("body_leggings");
        this.body_chestplate = this.body.getChild("body_chestplate");

        this.right_arm_chestplate = this.rightArm.getChild("right_arm_chestplate");

        this.left_arm_chestplate = this.leftArm.getChild("left_arm_chestplate");

        this.right_leg_leggings = this.rightLeg.getChild("right_leg_leggings");
        this.right_leg_boot = this.rightLeg.getChild("right_leg_boot");

        this.left_leg_leggings = this.leftLeg.getChild("left_leg_leggings");
        this.left_leg_boot = this.leftLeg.getChild("left_leg_boot");
    }

    public static ModelData getModelData(Consumer<ModelData> modelDefinition, Dilation dilation) {
        ModelData modelData = new ModelData();

        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelDefinition.accept(modelData);

        return modelData;
    }

    public Identifier getTexture() {
        return this.modelDefinition.getTexture();
    }

    public void renderParts(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, Part part) {
        toggleVisibility(part == Part.HELMET, part == Part.CHESTPLATE, part == Part.LEGGINGS, part == Part.BOOTS);
        render(matrices, vertices, light, overlay);
        toggleVisibility(true, true, true, true);
    }

    private void toggleVisibility(boolean helmet, boolean chestplate, boolean leggings, boolean boots) {
        this.helmet.visible = helmet;

        this.body_chestplate.visible = chestplate;
        this.right_arm_chestplate.visible = chestplate;
        this.left_arm_chestplate.visible = chestplate;

        this.body_leggings.visible = leggings;
        this.right_leg_leggings.visible = leggings;
        this.left_leg_leggings.visible = leggings;

        this.right_leg_boot.visible = boots;
        this.left_leg_boot.visible = boots;
    }

    public enum Part {
        HELMET, CHESTPLATE, LEGGINGS, BOOTS
    }

    public void renderHelmet(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.renderParts(matrices, vertices, light, overlay, Part.HELMET);
    }

    public void renderChestplate(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.renderParts(matrices, vertices, light, overlay, Part.CHESTPLATE);
    }

    public void renderLeggings(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.renderParts(matrices, vertices, light, overlay, Part.LEGGINGS);
    }

    public void renderBoots(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.renderParts(matrices, vertices, light, overlay, Part.BOOTS);
    }
}