package dev.doctor4t.ratatouille.mixin.client;

import dev.doctor4t.ratatouille.client.render.state.PlushOnHeadRenderStateAddition;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlushOnHeadRenderStateAddition {
    @Unique
    private final ItemRenderState plushOnHeadRenderState = new ItemRenderState();

    @Override
    public ItemRenderState ratatouille$getPlushOnHeadRenderState() {
        return plushOnHeadRenderState;
    }
}
