package com.brskng.optimized.compat;

import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class IronsLibTransmogContext {
    private static final ThreadLocal<LivingEntity> CURRENT = new ThreadLocal<>();
    public static void set(LivingEntity entity){ CURRENT.set(entity); }
    public static void clear(){ CURRENT.remove(); }
    public static boolean shouldSuppressTransmogActivation(){
        if(!BrskngsOptimizedClientConfig.ENABLE_IRONS_LIB_PATCHES.get()) return false;
        LivingEntity entity = CURRENT.get();
        Minecraft mc = Minecraft.getInstance();
        if(entity == null || mc.player == null || entity == mc.player) return false;
        if(BrskngsOptimizedClientConfig.IRONS_LIB_SKIP_TRANSMOG_FOR_NON_PLAYERS.get() && !(entity instanceof Player)) return true;
        double d = BrskngsOptimizedClientConfig.IRONS_LIB_TRANSMOG_RENDER_DISTANCE.get();
        return mc.player.distanceToSqr(entity) > d * d;
    }
    private IronsLibTransmogContext(){}
}
