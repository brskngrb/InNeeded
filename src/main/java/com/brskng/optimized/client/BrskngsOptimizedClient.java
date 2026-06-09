package com.brskng.optimized.client;

import com.brskng.optimized.BrskngsOptimized;
import com.brskng.optimized.client.hud.FpsHudOverlay;
import com.brskng.optimized.client.hud.FpsHudTracker;
import com.brskng.optimized.client.profiler.RenderThreadProfiler;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@EventBusSubscriber(modid = BrskngsOptimized.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public final class BrskngsOptimizedClient {
    public static final KeyMapping OPEN_CONFIG_KEY = new KeyMapping("key.brskngs_optimized.open_config", InputConstants.Type.KEYSYM, InputConstants.UNKNOWN.getValue(), "key.categories.brskngs_optimized");

    public static void registerConfigScreen(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> new ConfigurationScreen(container, parent));
    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_CONFIG_KEY);
    }

    @EventBusSubscriber(modid = BrskngsOptimized.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static final class GameBusEvents {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            com.brskng.optimized.compat.CreateKineticRenderBudget.resetTick();
            Minecraft mc = Minecraft.getInstance();
            while (OPEN_CONFIG_KEY.consumeClick()) {
                if (mc.screen == null && BrskngsOptimized.CONTAINER != null) {
                    mc.setScreen(new ConfigurationScreen(BrskngsOptimized.CONTAINER, null));
                }
            }
        }

        @SubscribeEvent
        public static void onRenderGui(RenderGuiEvent.Post event) {
            RenderThreadProfiler.captureRenderThread(Thread.currentThread());
            FpsHudTracker.frame();
            FpsHudOverlay.render(event.getGuiGraphics());
        }

        @SubscribeEvent
        public static void registerClientCommands(RegisterClientCommandsEvent event) {
            event.getDispatcher().register(
                    Commands.literal("brskngopt")
                            .then(Commands.literal("renderprofile")
                                    .then(Commands.literal("start")
                                            .then(Commands.argument("seconds", IntegerArgumentType.integer(1, 600))
                                                    .then(Commands.argument("intervalMs", IntegerArgumentType.integer(1, 1000))
                                                            .executes(context -> {
                                                                int seconds = IntegerArgumentType.getInteger(context, "seconds");
                                                                int intervalMs = IntegerArgumentType.getInteger(context, "intervalMs");
                                                                RenderThreadProfiler.start(seconds, intervalMs);
                                                                return 1;
                                                            }))))
                                    .then(Commands.literal("stop")
                                            .executes(context -> {
                                                RenderThreadProfiler.stop();
                                                return 1;
                                            }))
                                    .then(Commands.literal("status")
                                            .executes(context -> {
                                                RenderThreadProfiler.status();
                                                return 1;
                                            })))
            );
        }
    }

    private BrskngsOptimizedClient() {}
}
