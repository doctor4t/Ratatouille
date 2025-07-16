package dev.doctor4t.ratatouille.client.model.armor;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class CustomBipedArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public final ModelPart head;
    public final ModelPart body;
    public final ModelPart body_chestplate;
    public final ModelPart body_leggings;
    public final ModelPart right_arm;
    public final ModelPart left_arm;
    public final ModelPart right_leg;
    public final ModelPart right_leg_leggings;
    public final ModelPart right_leg_boot;
    public final ModelPart left_leg;
    public final ModelPart left_leg_leggings;
    public final ModelPart left_leg_boot;
    private final CustomArmorModelDefinition modelDefinition;

    public CustomBipedArmorModel(ModelPart root, CustomArmorModelDefinition modelDefinition) {
        super(root);

        this.modelDefinition = modelDefinition;

        this.head = root.getChild("head");

        this.body = root.getChild("body");
        this.body_leggings = this.body.getChild("body_leggings");
        this.body_chestplate = this.body.getChild("body_chestplate");

        this.right_arm = root.getChild("right_arm");

        this.left_arm = root.getChild("left_arm");

        this.right_leg = root.getChild("right_leg");
        this.right_leg_leggings = this.right_leg.getChild("right_leg_leggings");
        this.right_leg_boot = this.right_leg.getChild("right_leg_boot");

        this.left_leg = root.getChild("left_leg");
        this.left_leg_leggings = this.left_leg.getChild("left_leg_leggings");
        this.left_leg_boot = this.left_leg.getChild("left_leg_boot");
    }

    public static ModelData getModelData(Consumer<ModelData> modelDefinition, Dilation dilation) {
        ModelData modelData = BipedEntityModel.getModelData(dilation, 0.0F);
        modelDefinition.accept(modelData);
        return modelData;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        head.render(matrices, vertices, light, overlay, color);
        body.render(matrices, vertices, light, overlay, color);
        right_arm.render(matrices, vertices, light, overlay, color);
        left_arm.render(matrices, vertices, light, overlay, color);
        right_leg.render(matrices, vertices, light, overlay, color);
        left_leg.render(matrices, vertices, light, overlay, color);
    }

    public Identifier getTexture() {
        return this.modelDefinition.getTexture();
    }
}