package dev.doctor4t.ratatouille.client.model.armor;

import dev.doctor4t.ratatouille.Ratatouille;
import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;

public class TestArmorModelDefinition extends CustomArmorModelDefinition {
    public static final Identifier TEXTURE = Ratatouille.id("textures/entity/armor/test.png");

    @Override
    public void addModelParts(ModelData modelData) {

        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData helmet = head.addChild("helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F))
                .uv(0, 0).cuboid(-4.0F, -32.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.65F))
                .uv(32, -14).cuboid(0.0F, -35.0F, -7.0F, 0.0F, 11.0F, 14.0F, new Dilation(0.01F))
                .uv(60, 0).cuboid(-7.0F, -35.0F, 0.0F, 14.0F, 11.0F, 0.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body_chestplate = body.addChild("body_chestplate", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData OxygenTank2_r1 = body_chestplate.addChild("OxygenTank2_r1", ModelPartBuilder.create().uv(86, 64).cuboid(-3.5F, -12.0F, 0.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
                .uv(86, 64).cuboid(1.5F, -12.0F, 0.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -14.0F, 2.0F, -0.1745F, 0.0F, 0.0F));

        ModelPartData Boobs_r1 = body_chestplate.addChild("Boobs_r1", ModelPartBuilder.create().uv(48, 11).cuboid(-4.0F, -7.75F, -3.0F, 8.0F, 7.0F, 3.0F, new Dilation(0.53F)), ModelTransform.of(0.0F, -15.0F, 0.0F, 0.48F, 0.0F, 0.0F));

        ModelPartData body_leggings = body.addChild("body_leggings", ModelPartBuilder.create().uv(24, 16).cuboid(-4.0F, -9.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, 9.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData right_arm_chestplate = right_arm.addChild("right_arm_chestplate", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F))
                .uv(48, 21).cuboid(-8.25F, -24.25F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.75F)), ModelTransform.pivot(5.0F, 22.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create(), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData left_arm_chestplate = left_arm.addChild("left_arm_chestplate", ModelPartBuilder.create().uv(16, 32).cuboid(4.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F))
                .uv(66, 21).cuboid(4.25F, -24.25F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.75F)), ModelTransform.pivot(-5.0F, 22.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create(), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData right_leg_leggings = right_leg.addChild("right_leg_leggings", ModelPartBuilder.create().uv(32, 32).cuboid(-3.9F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData right_leg_boot = right_leg.addChild("right_leg_boot", ModelPartBuilder.create().uv(48, 32).cuboid(-4.0F, -12.25F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.55F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create(), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        ModelPartData left_leg_leggings = left_leg.addChild("left_leg_leggings", ModelPartBuilder.create().uv(64, 32).cuboid(-0.1F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData left_leg_boot = left_leg.addChild("left_leg_boot", ModelPartBuilder.create().uv(80, 32).cuboid(0.0F, -12.25F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.55F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

    }

    @Override
    public Identifier getTexture() {
        return TEXTURE;
    }
}
