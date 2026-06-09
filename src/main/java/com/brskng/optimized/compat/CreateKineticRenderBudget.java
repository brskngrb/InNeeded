package com.brskng.optimized.compat;

import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Locale;

public final class CreateKineticRenderBudget {
    private static int instancesThisTick = 0;
    public static void resetTick(){ instancesThisTick = 0; }
    public static float optimizeSpeed(Object be, float speed){
        try{
            if(!BrskngsOptimizedClientConfig.CREATE_ENABLED.get()) return speed;
            Minecraft mc = Minecraft.getInstance();
            if(mc.player == null || mc.level == null || be == null) return speed;
            BlockPos pos = (BlockPos) be.getClass().getMethod("getBlockPos").invoke(be);
            BlockState state = (BlockState) be.getClass().getMethod("getBlockState").invoke(be);
            String id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
            if(BrskngsOptimizedClientConfig.CREATE_WHITELIST.get().contains(id)) return speed;
            if(!affects(id)) return speed;
            if(instancesThisTick++ > BrskngsOptimizedClientConfig.CREATE_MAX_INSTANCES.get()) return 0f;
            Player p = mc.player;
            double dx = p.getX() - (pos.getX() + 0.5), dy = p.getY() - (pos.getY() + 0.5), dz = p.getZ() - (pos.getZ() + 0.5);
            double distSq = dx * dx + dy * dy + dz * dz;
            if(BrskngsOptimizedClientConfig.CREATE_VIEW_PRIORITY.get() && isLookingAt(p, pos)) return speed;
            double cull = BrskngsOptimizedClientConfig.CREATE_CULLING_DISTANCE.get();
            if(distSq > cull * cull) return 0f;
            double lod = BrskngsOptimizedClientConfig.CREATE_LOD_DISTANCE.get();
            if(distSq > lod * lod){
                int interval = BrskngsOptimizedClientConfig.CREATE_UPDATE_INTERVAL.get();
                if(interval > 1 && (mc.level.getGameTime() % interval) != 0) return 0f;
                return speed * BrskngsOptimizedClientConfig.CREATE_LOD_SPEED_MULTIPLIER.get().floatValue();
            }
            return speed;
        } catch(Throwable t){
            return speed;
        }
    }
    private static boolean affects(String id){
        String s = id.toLowerCase(Locale.ROOT);
        if(s.contains("shaft") && !BrskngsOptimizedClientConfig.CREATE_AFFECT_SHAFTS.get()) return false;
        if((s.contains("cog") || s.contains("wheel")) && !BrskngsOptimizedClientConfig.CREATE_AFFECT_COGWHEELS.get()) return false;
        if(s.contains("gearbox") && !BrskngsOptimizedClientConfig.CREATE_AFFECT_GEARBOXES.get()) return false;
        if(s.contains("chain") && !BrskngsOptimizedClientConfig.CREATE_AFFECT_CHAINS.get()) return false;
        return true;
    }
    private static boolean isLookingAt(Player p, BlockPos pos){
        var look = p.getLookAngle().normalize();
        var to = new net.minecraft.world.phys.Vec3(pos.getX() + 0.5 - p.getX(), pos.getY() + 0.5 - p.getY(), pos.getZ() + 0.5 - p.getZ()).normalize();
        return look.dot(to) > BrskngsOptimizedClientConfig.CREATE_VIEW_PRIORITY_DOT.get();
    }
    private CreateKineticRenderBudget(){}
}
