package com.brskng.optimized.mixin.superbwarfare;
import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo @Mixin(targets="com.atsuishio.superbwarfare.client.renderer.entity.VehicleRenderer", remap=false)
public abstract class SuperbWarfareVehicleRendererMixin {
    @Inject(method="render(Lcom/atsuishio/superbwarfare/entity/vehicle/base/VehicleEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at=@At("HEAD"), cancellable=true, remap=false)
    private void skipFar(@Coerce Object obj, float yaw, float tick, PoseStack ps, MultiBufferSource bs, int light, CallbackInfo ci){
        if(!BrskngsOptimizedClientConfig.ENABLE_SUPERB_WARFARE_PATCHES.get() || !BrskngsOptimizedClientConfig.SUPERB_WARFARE_FORCE_DISTANCE_CHECK_IN_RENDER.get()) return;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !(obj instanceof Entity e)) return;
        double d = BrskngsOptimizedClientConfig.SUPERB_WARFARE_VEHICLE_RENDER_DISTANCE.get();
        if(mc.player.distanceToSqr(e) > d * d) ci.cancel();
    }
}
