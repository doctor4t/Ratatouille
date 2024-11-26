package dev.doctor4t.ratatouille.client.lib.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity;

public abstract class CosmeticsScreen<T extends CosmeticsLocalData> extends Screen {
    protected final T data;
    private final PlayerEntity player;
    protected int x;
    protected int y;
    boolean locked;

    public CosmeticsScreen(Text title, T data, boolean locked) {
        super(title);
        MinecraftClient client = MinecraftClient.getInstance();
        this.player = client.player;
        this.data = data;
        this.locked = locked;
    }

    public T getData() {
        return data;
    }

    @Override
    protected void init() {
        this.x = (this.width / 2) - (CosmeticsScreenUVs.BACKGROUND.getWidth() / 2);
        this.y = (this.height / 2) - (CosmeticsScreenUVs.BACKGROUND.getHeight() / 2);

        // cancel and confirm
        this.addDrawableChild(new ExitButtonWidget(this.x + 104, y + 109, Text.empty(), CosmeticsScreenUVs.CANCEL, CosmeticsScreenUVs.CANCEL_HOVER, () -> {
        }, false));
        this.addDrawableChild(new ExitButtonWidget(this.x + 128, y + 109, Text.literal("TEST"), this.locked ? CosmeticsScreenUVs.LOCKED : CosmeticsScreenUVs.CONFIRM, this.locked ? CosmeticsScreenUVs.LOCKED : CosmeticsScreenUVs.CONFIRM_HOVER, () -> this.getData().uploadToServer(), this.locked));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawTexture(CosmeticsScreenUVs.GUI_TEXTURE, this.x, this.y, 0, 0, CosmeticsScreenUVs.BACKGROUND.getWidth(), CosmeticsScreenUVs.BACKGROUND.getHeight());
        if (this.player != null) {
            drawEntity(context, this.x + 39, this.y + 123, 46, (float) ((this.x + 39) - mouseX) / 10f, (float) ((this.y + 46) - mouseY) / 10f, this.player);
        }
        context.drawText(this.textRenderer, this.title, this.width / 2 - this.textRenderer.getWidth(this.title) / 2, this.y + 7, 4210752, false);
        super.render(context, mouseX, mouseY, delta);
    }

    private static class ExitButtonWidget extends PressableWidget {
        Runnable runnable;
        private final CosmeticsScreenUVs texture;
        private final CosmeticsScreenUVs hoverTexture;
        private final boolean locked;

        ExitButtonWidget(int x, int y, Text text, CosmeticsScreenUVs texture, CosmeticsScreenUVs hoverTexture, Runnable runnable, boolean locked) {
            super(x, y, CosmeticsScreenUVs.CANCEL.getWidth(), CosmeticsScreenUVs.CANCEL.getHeight(), text);
            this.runnable = runnable;
            this.texture = texture;
            this.hoverTexture = hoverTexture;
            this.locked = locked;
            if (this.locked) {
                this.setTooltip(Tooltip.of(Text.translatable("tooltip.supporter_only")));
            }
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            CosmeticsScreenUVs icon = this.isMouseOver(mouseX, mouseY) ? hoverTexture : texture;
            context.drawTexture(CosmeticsScreenUVs.GUI_TEXTURE, this.getX(), this.getY(), icon.getU(), icon.getV(), icon.getWidth(), icon.getHeight());
        }

        @Override
        public void onPress() {
            if (!this.locked) {
                runnable.run();
                Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                if (currentScreen != null) {
                    currentScreen.close();
                }
            }
        }

        @Override
        public void playDownSound(SoundManager soundManager) {
            if (!this.locked) super.playDownSound(soundManager);
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        }
    }
}
