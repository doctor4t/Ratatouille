package dev.doctor4t.ratatouille.client.gui;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.lib.gui.CosmeticsScreen;
import dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics;
import dev.doctor4t.ratatouille.util.TextUtils;
import dev.upcraft.datasync.api.util.Entitlements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.text.Text;

public class PlushOnHeadCosmeticsScreen extends CosmeticsScreen<PlushOnHeadCosmeticsLocalData> {
    public static final String TITLE = "options.plush_on_head_cosmetics";

    public PlushOnHeadCosmeticsScreen() {
        super(Text.translatable(TITLE), new PlushOnHeadCosmeticsLocalData(), Ratatouille.isSupporter(MinecraftClient.getInstance().player.getUuid()));
    }

    @Override
    protected void init() {
        super.init();

        // cosmetic options
        PlushOnHeadCosmetics.Plush[] plushies = PlushOnHeadCosmetics.Plush.values();
        int optionButtonWidth = 104;
        int optionButtonHeight = 20;
        this.addDrawableChild(
                CyclingButtonWidget.<PlushOnHeadCosmetics.Plush>builder(value -> Text.of(TextUtils.formatValueString(value.name)))
                        .initially(this.data.getPlush())
                        .values(plushies)
                        .build(this.x + 72, this.y + 19, optionButtonWidth, optionButtonHeight, Text.translatable(TITLE + ".plush"), (button, value) -> this.data.setPlush(value)));
    }
}
