package com.brskng.optimized;

import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(BrskngsOptimized.MOD_ID)
public final class BrskngsOptimized {
    public static final String MOD_ID = "brskngs_optimized";
    public static ModContainer CONTAINER;

    public BrskngsOptimized(ModContainer container) {
        CONTAINER = container;
        container.registerConfig(ModConfig.Type.CLIENT, BrskngsOptimizedClientConfig.SPEC, "brskngs-optimized-client.toml");
        if (FMLEnvironment.dist == Dist.CLIENT) {
            com.brskng.optimized.client.BrskngsOptimizedClient.registerConfigScreen(container);
        }
    }
}
