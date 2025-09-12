package dev.doctor4t.ratatouille.mixin.client.optionlock;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.ratatouille.client.util.OptionLocker;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OptionListWidget.OptionWidgetEntry.class)
public class OptionWidgetEntryMixin {
    @WrapOperation(method = "create(Lnet/minecraft/client/option/GameOptions;Lnet/minecraft/client/option/SimpleOption;Lnet/minecraft/client/gui/screen/option/GameOptionsScreen;)Lnet/minecraft/client/gui/widget/OptionListWidget$OptionWidgetEntry;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;createWidget(Lnet/minecraft/client/option/GameOptions;III)Lnet/minecraft/client/gui/widget/ClickableWidget;"))
    private static ClickableWidget ratatouille$deactivateOptionWidget(SimpleOption<?> instance, GameOptions options, int x, int y, int width, Operation<ClickableWidget> original) {
        return getModifiedWidget(instance, original.call(instance, options, x, y, width));
    }

    @WrapOperation(method = "create(Lnet/minecraft/client/option/GameOptions;Lnet/minecraft/client/option/SimpleOption;Lnet/minecraft/client/option/SimpleOption;Lnet/minecraft/client/gui/screen/option/GameOptionsScreen;)Lnet/minecraft/client/gui/widget/OptionListWidget$OptionWidgetEntry;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;createWidget(Lnet/minecraft/client/option/GameOptions;)Lnet/minecraft/client/gui/widget/ClickableWidget;"))
    private static ClickableWidget ratatouille$deactivateOptionWidget(SimpleOption<?> instance, GameOptions options, Operation<ClickableWidget> original) {
        return getModifiedWidget(instance, original.call(instance, options));
    }

    @Unique
    private static ClickableWidget getModifiedWidget(SimpleOption<?> instance, ClickableWidget widget) {
        if (OptionLocker.isOptionLocked(instance)) {
            widget.active = false;
        }
        return widget;
    }
}
