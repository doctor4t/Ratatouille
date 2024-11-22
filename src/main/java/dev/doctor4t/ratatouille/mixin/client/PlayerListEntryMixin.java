package dev.doctor4t.ratatouille.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public abstract class PlayerListEntryMixin {
    private static final Identifier ICONS_TEXTURE = new Identifier("textures/gui/icons.png");

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V", shift = At.Shift.BEFORE))
    public void ratatouille$injectSupporterIcon(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci, @Local(index = 13) LocalIntRef o, @Local(index = 23) LocalIntRef w, @Local boolean bl, @Local(index = 24) LocalIntRef x, @Local(ordinal = 0) PlayerListEntry playerListEntry2) {
        /*
            Method signature memo:
            @Local allows to access a field in the method, index is its ISTORE value in the bytecode (view > show bytecode, you bound it to ctrl + shift + numpad 5 future RAT)
            LocalIntRef allows to change that field with .set()
         */
        this.renderSupporterIcon(context, o.get(), w.get() - (bl ? 9 : 0), x.get(), playerListEntry2);
    }

    @Unique
    protected void renderSupporterIcon(DrawContext context, int width, int x, int y, PlayerListEntry entry) {
        int i = 0;
        int j;
        if (entry.getLatency() < 0) {
            j = 5;
        } else if (entry.getLatency() < 150) {
            j = 0;
        } else if (entry.getLatency() < 300) {
            j = 1;
        } else if (entry.getLatency() < 600) {
            j = 2;
        } else if (entry.getLatency() < 1000) {
            j = 3;
        } else {
            j = 4;
        }

        context.getMatrices().push();
        context.getMatrices().translate(0.0F, 0.0F, 100.0F);
        context.drawTexture(ICONS_TEXTURE, x + width - 11, y, 0, 176 + j * 8, 20, 8);
        context.getMatrices().pop();
    }

}
