package dev.doctor4t.ratatouille.mixin.client.optionlock;

import com.mojang.serialization.Codec;
import dev.doctor4t.ratatouille.client.util.OptionLocker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> {
    @Shadow
    T value;

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraft/client/option/SimpleOption$TooltipFactory;Lnet/minecraft/client/option/SimpleOption$ValueTextGetter;Lnet/minecraft/client/option/SimpleOption$Callbacks;Lcom/mojang/serialization/Codec;Ljava/lang/Object;Ljava/util/function/Consumer;)V", at = @At("TAIL"))
    public void ratatouille$addOptionKeyToMap(String key, SimpleOption.TooltipFactory tooltipFactory, SimpleOption.ValueTextGetter valueTextGetter, SimpleOption.Callbacks callbacks, Codec codec, Object defaultValue, Consumer changeCallback, CallbackInfo ci) {
        OptionLocker.OPTIONS_KEYS.put((SimpleOption<?>) (Object) this, key);

        Optional<?> overriddenValue = OptionLocker.getOverriddenValueOf(key);
        overriddenValue.ifPresent(o -> this.value = (T) o);
    }

    @ModifyVariable(method = "setValue", at = @At("HEAD"), argsOnly = true)
    public T ratatouille$overrideValue(T value) {
        Optional<?> overriddenValue = OptionLocker.getOverriddenValueOf(OptionLocker.OPTIONS_KEYS.get((SimpleOption<?>) (Object) this));
        if (overriddenValue.isPresent()) {
            return (T) overriddenValue.get();
        } else {
            return value;
        }
    }
}
