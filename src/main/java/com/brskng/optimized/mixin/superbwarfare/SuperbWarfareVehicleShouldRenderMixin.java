package com.brskng.optimized.mixin.superbwarfare;
import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo @Mixin(targets="com.atsuishio.superbwarfare.client.renderer.entity.VehicleRenderer", remap=false)
public abstract class SuperbWarfareVehicleShouldRenderMixin {
    @Inject(method="shouldRender(Lcom/atsuishio/superbwarfare/entity/vehicle/base/VehicleEntity;Lnet/minecraft/client/renderer/culling/Frustum;DDD)Z", at=@At("HEAD"), cancellable=true, remap=false)
    private void limit(@Coerce Object obj, Frustum f, double x, double y, double z, CallbackInfoReturnable<Boolean> cir){
        if(!BrskngsOptimizedClientConfig.ENABLE_SUPERB_WARFARE_PATCHES.get() || !(obj instanceof Entity e)) return;
        double d = BrskngsOptimizedClientConfig.SUPERB_WARFARE_VEHICLE_RENDER_DISTANCE.get();
        double dx = e.getX() - x, dy = e.getY() - y, dz = e.getZ() - z;
        if(dx * dx + dy * dy + dz * dz > d * d) cir.setReturnValue(false);
    }
}
