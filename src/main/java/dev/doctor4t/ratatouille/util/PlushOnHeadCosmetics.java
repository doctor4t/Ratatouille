package dev.doctor4t.ratatouille.util;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.gui.PlushOnHeadCosmeticsScreen;
import dev.doctor4t.ratatouille.index.RatatouilleBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public abstract class PlushOnHeadCosmetics {
    public static Plush getPlush(UUID uuid) {
        // return ongoing customization option if customizing
        if (MinecraftClient.getInstance().currentScreen instanceof PlushOnHeadCosmeticsScreen plushOnHeadCosmeticsScreen) {
            return plushOnHeadCosmeticsScreen.getData().getPlush();
        }

        // otherwise return saved option
        Optional<PlushOnHeadSupporterData> optional = Ratatouille.PLUSH_ON_HEAD_DATA.get(uuid);
        if (optional.isPresent()) {
            try {
                return Plush.fromString(optional.get().plush());
            } catch (IllegalArgumentException ignored) {
            }
        }

        return Plush.NONE;
    }

    public enum Plush {
        NONE(Items.AIR),
        RAT_MAID(RatatouilleBlocks.RAT_MAID_PLUSH.asItem()),
        FOLLY(RatatouilleBlocks.FOLLY_PLUSH.asItem()),
        MAUVE(RatatouilleBlocks.MAUVE_PLUSH.asItem());

        public final String name;
        @NotNull
        public final Item item;

        Plush(@NotNull Item item) {
            this.name = this.toString().toLowerCase(Locale.ROOT);
            this.item = item;
        }

        @Nullable
        public static PlushOnHeadCosmetics.Plush fromString(String name) {
            for (Plush plush : Plush.values()) if (plush.name.equalsIgnoreCase(name)) return plush;
            return null;
        }
    }
}
