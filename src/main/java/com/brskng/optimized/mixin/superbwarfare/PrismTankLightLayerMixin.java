package com.brskng.optimized.mixin.superbwarfare;
import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo @Mixin(targets="com.atsuishio.superbwarfare.client.layer.vehicle.PrismTankLightLayer", remap=false)
public abstract class PrismTankLightLayerMixin {
    @Inject(method="render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/atsuishio/superbwarfare/entity/vehicle/PrismTankEntity;Lsoftware/bernie/geckolib/cache/object/BakedGeoModel;Lnet/minecraft/client/renderer/RenderType;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/VertexConsumer;FII)V", at=@At("HEAD"), cancellable=true, remap=false)
    private void skip(PoseStack ps, @Coerce Object anim, @Coerce Object model, RenderType rt, MultiBufferSource bs, VertexConsumer vc, float tick, int light, int overlay, CallbackInfo ci){
        if(!BrskngsOptimizedClientConfig.ENABLE_SUPERB_WARFARE_PATCHES.get()) return;
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null || !(anim instanceof Entity e)) return;
        double d = BrskngsOptimizedClientConfig.SUPERB_WARFARE_PRISM_LIGHT_RENDER_DISTANCE.get();
        if(d <= 0 || mc.player.distanceToSqr(e) > d * d) ci.cancel();
    }
}
