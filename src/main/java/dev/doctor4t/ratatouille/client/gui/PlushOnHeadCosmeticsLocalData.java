package dev.doctor4t.ratatouille.client.gui;

import dev.doctor4t.ratatouille.Ratatouille;
import dev.doctor4t.ratatouille.client.lib.gui.CosmeticsLocalData;
import dev.doctor4t.ratatouille.util.PlushOnHeadCosmetics;
import dev.doctor4t.ratatouille.util.PlushOnHeadSupporterData;
import net.minecraft.client.MinecraftClient;

import java.util.UUID;

public class PlushOnHeadCosmeticsLocalData implements CosmeticsLocalData {
    private PlushOnHeadCosmetics.Plush plush = PlushOnHeadCosmetics.Plush.NONE;

    public PlushOnHeadCosmeticsLocalData() {
        UUID uuid = MinecraftClient.getInstance().player.getUuid();
        this.setPlush(PlushOnHeadCosmetics.getPlush(uuid));
    }

    public PlushOnHeadCosmetics.Plush getPlush() {
        return plush;
    }

    public void setPlush(PlushOnHeadCosmetics.Plush plush) {
        this.plush = plush;
    }

    @Override
    public void uploadToServer() {
        PlushOnHeadSupporterData newData = new PlushOnHeadSupporterData(plush.name);
        Ratatouille.PLUSH_ON_HEAD_DATA.setData(newData);
    }
}
