package dev.doctor4t.ratatouille.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.ratatouille.util.SupporterUtils;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerListHud.class)
public abstract class PlayerListEntryMixin {
    @ModifyReturnValue(method = "applyGameModeFormatting", at = @At("RETURN"))
    public Text ratatouille$applySupporterFormattingToName(Text original, PlayerListEntry entry) {
        return SupporterUtils.getSupporterStylisedName(entry.getProfile().getId(), original);
    }
}