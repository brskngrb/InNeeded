package com.brskng.optimized.mixin.superbwarfare;
import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo @Mixin(targets="com.atsuishio.superbwarfare.client.renderer.entity.VehicleRenderer", remap=false)
public abstract class SuperbWarfareVehicleRenderTypeMixin {
    @Inject(method="getRenderType(Lcom/atsuishio/superbwarfare/entity/vehicle/base/VehicleEntity;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/renderer/MultiBufferSource;F)Lnet/minecraft/client/renderer/RenderType;", at=@At("HEAD"), cancellable=true, remap=false)
    private void cutout(@Coerce Object obj, ResourceLocation tex, MultiBufferSource bs, float tick, CallbackInfoReturnable<RenderType> cir){
        if(!BrskngsOptimizedClientConfig.ENABLE_SUPERB_WARFARE_PATCHES.get() || !BrskngsOptimizedClientConfig.SUPERB_WARFARE_USE_CUTOUT_RENDER_TYPE.get()) return;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !(obj instanceof Entity e)) return;
        double d = BrskngsOptimizedClientConfig.SUPERB_WARFARE_CUTOUT_RENDER_TYPE_DISTANCE.get();
        if(mc.player.distanceToSqr(e) > d * d) cir.setReturnValue(RenderType.entityCutoutNoCull(tex));
    }
}
