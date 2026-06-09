package com.brskng.optimized.compat;

import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import java.util.Locale;

public final class TouhouLittleMaidLodHelper {
    public static boolean isMaid(Entity entity){
        if(entity == null) return false;
        String name = entity.getClass().getName().toLowerCase(Locale.ROOT);
        return name.contains("touhoulittlemaid") && (name.contains("maid") || name.contains("entitymaid"));
    }
    public static boolean shouldCull(Entity entity){
        if(!BrskngsOptimizedClientConfig.tlmCullingEnabled() || !isMaid(entity)) return false;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return false;
        double d = BrskngsOptimizedClientConfig.TLM_CULLING_DISTANCE.get();
        return mc.player.distanceToSqr(entity) > d * d;
    }
    public static boolean shouldUseLod(Entity entity){
        if(!BrskngsOptimizedClientConfig.tlmLodEnabled() || !isMaid(entity)) return false;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return false;
        double d = BrskngsOptimizedClientConfig.TLM_LOD_DISTANCE.get();
        return mc.player.distanceToSqr(entity) > d * d;
    }
    private TouhouLittleMaidLodHelper(){}
}
