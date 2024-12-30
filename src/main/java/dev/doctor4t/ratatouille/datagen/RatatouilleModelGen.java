package dev.doctor4t.ratatouille.datagen;

import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class RatatouilleModelGen extends FabricModelProvider {
    public RatatouilleModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerNorthDefaultHorizontalRotation(RatatouilleBlocks.RAT_MAID_PLUSH);
        generator.registerNorthDefaultHorizontalRotation(RatatouilleBlocks.FOLLY_PLUSH);
        generator.registerNorthDefaultHorizontalRotation(RatatouilleBlocks.MAUVE_PLUSH);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
    }

    protected BlockStateVariant variant() {
        return BlockStateVariant.create();
    }

    protected <T> BlockStateVariant variant(VariantSetting<T> variantSetting, T value) {
        return this.variant().put(variantSetting, value);
    }

    protected <T> BlockStateVariant variant(Identifier model, VariantSetting<T> variantSetting, T value) {
        return this.model(model).put(variantSetting, value);
    }

    protected BlockStateVariant model(Identifier model) {
        return this.variant(VariantSettings.MODEL, model);
    }

    protected BlockStateVariant rotateForFace(BlockStateVariant variant, Direction direction, boolean uvlock) {
        if (uvlock) variant.put(VariantSettings.UVLOCK, true);
        switch (direction) {
            case EAST -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R90);
            case SOUTH -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R180);
            case WEST -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R270);
            case UP -> variant.put(VariantSettings.X, VariantSettings.Rotation.R270);
            case DOWN -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
        }
        return variant;
    }

    protected BlockStateVariant rotateForAxis(BlockStateVariant variant, Direction.Axis axis) {
        return switch (axis) {
            case X -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R270);
            case Y -> variant.put(VariantSettings.X, VariantSettings.Rotation.R90);
            case Z -> variant;
        };
    }
}
