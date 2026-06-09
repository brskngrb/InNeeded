package com.brskng.optimized.client.hud;

import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public final class FpsHudOverlay {
    public static void render(GuiGraphics graphics) {
        if (!BrskngsOptimizedClientConfig.FPS_HUD_ENABLED.get()) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.hideGui) return;
        int x = BrskngsOptimizedClientConfig.FPS_HUD_X.get();
        int y = BrskngsOptimizedClientConfig.FPS_HUD_Y.get();
        String line1 = Component.translatable("brskngs_optimized.hud.fps", FpsHudTracker.currentFps()).getString();
        String line2 = Component.translatable("brskngs_optimized.hud.avg_fps", FpsHudTracker.averageFps()).getString();
        String line3 = Component.translatable("brskngs_optimized.hud.one_percent_low", FpsHudTracker.onePercentLowFps()).getString();
        int color = BrskngsOptimizedClientConfig.FPS_HUD_TEXT_COLOR.get();
        if (BrskngsOptimizedClientConfig.FPS_HUD_BACKGROUND.get()) {
            int width = Math.max(minecraft.font.width(line1), Math.max(minecraft.font.width(line2), minecraft.font.width(line3)));
            graphics.fill(x - 3, y - 3, x + width + 3, y + 30, 0x80000000);
        }
        graphics.drawString(minecraft.font, line1, x, y, color, true);
        graphics.drawString(minecraft.font, line2, x, y + 10, color, true);
        graphics.drawString(minecraft.font, line3, x, y + 20, color, true);
    }
    private FpsHudOverlay() {}
}
