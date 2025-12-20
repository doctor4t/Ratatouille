package dev.doctor4t.ratatouille.mixin.client.armor;

import dev.doctor4t.ratatouille.client.render.feature.MasterCustomBipedArmorFeatureRenderer;
import dev.doctor4t.ratatouille.client.render.feature.RendersArmInFirstPerson;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    @Unique
    protected List<FeatureRenderer<T, M>> firstPersonArmFeatures = new ArrayList<>();

    protected LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Shadow
    public abstract M getModel();

    @Shadow
    protected abstract boolean addFeature(FeatureRenderer<T, M> feature);

    @Inject(method = "addFeature", at = @At("TAIL"))
    private void ratatouille$registerArmFeatureRenderer(FeatureRenderer<T, M> feature, CallbackInfoReturnable<Boolean> cir) {
        if (feature instanceof RendersArmInFirstPerson<?>) {
            this.firstPersonArmFeatures.add(feature);
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ratatouille$registerCustomBipedArmorFeatureRenderer(EntityRendererFactory.Context ctx, EntityModel model, float shadowRadius, CallbackInfo ci) {
        if (model instanceof BipedEntityModel<?>) {
            this.addFeature(new MasterCustomBipedArmorFeatureRenderer(this, ctx));
        }
    }
}
