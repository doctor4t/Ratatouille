package dev.doctor4t.ratatouille.client.model.armor;

import net.minecraft.client.model.ModelData;
import net.minecraft.util.Identifier;

public abstract class CustomArmorModelDefinition {
    public abstract void addModelParts(ModelData modelData);

    public abstract Identifier getTexture();
}
