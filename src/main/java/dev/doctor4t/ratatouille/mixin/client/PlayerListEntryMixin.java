package dev.doctor4t.ratatouille.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.util.RatatouilleUtils;
import dev.upcraft.datasync.api.util.Entitlements;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Optional;

@Mixin(PlayerListHud.class)
public abstract class PlayerListEntryMixin {
    @ModifyReturnValue(method = "applyGameModeFormatting", at = @At("RETURN"))
    public Text ratatouille$applySupporterFormattingToName(Text original, PlayerListEntry entry) {
        return RatatouilleUtils.getSupporterStylisedName(entry.getProfile().getId(), original);
    }
}
