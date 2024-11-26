package dev.doctor4t.ratatouille.client.lib.gui;

import dev.doctor4t.ratatouille.Ratatouille;
import net.minecraft.util.Identifier;

public enum CosmeticsScreenUVs {
    BACKGROUND(0, 0, 182, 135),
    CANCEL(0, 135, 18, 18),
    CANCEL_HOVER(0, 153, 18, 18),
    CONFIRM(18, 135, 18, 18),
    CONFIRM_HOVER(18, 153, 18, 18);

    public static final Identifier GUI_TEXTURE = Ratatouille.id("textures/gui/cosmetics.png");
    private final int u;
    private final int v;
    private final int width;
    private final int height;

    CosmeticsScreenUVs(int u, int v, int width, int height) {
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
    }

    public int getU() {
        return this.u;
    }

    public int getV() {
        return this.v;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}