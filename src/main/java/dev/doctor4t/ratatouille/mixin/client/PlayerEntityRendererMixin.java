package dev.doctor4t.ratatouille.mixin.client;

import dev.doctor4t.ratatouille.client.render.feature.PlushOnHeadFeatureRenderer;
import dev.doctor4t.ratatouille.client.render.state.PlushOnHeadRenderStateAddition;
import dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemDisplayContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {

	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void ratatouille$registerPlushOnHeadFeatureRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo callbackInfo) {
		addFeature(new PlushOnHeadFeatureRenderer<>(this));
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void ratatouille$updatePlushOnHeadRenderState(AbstractClientPlayerEntity player, PlayerEntityRenderState state, float tickDelta, CallbackInfo ci) {
		PlushOnHeadCosmetics.Plush plush = PlushOnHeadCosmetics.getPlush(player.getUuid());
		if (state instanceof PlushOnHeadRenderStateAddition plushState) {
			if (plush == PlushOnHeadCosmetics.Plush.NONE) {
				plushState.ratatouille$getPlushOnHeadRenderState().clear();
			} else {
				itemModelResolver.updateForLivingEntity(plushState.ratatouille$getPlushOnHeadRenderState(), plush.item.getDefaultStack(), ItemDisplayContext.HEAD, player);
			}
		}
	}
}
