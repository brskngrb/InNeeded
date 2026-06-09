package com.brskng.optimized.mixin.ironslib;
import com.brskng.optimized.compat.IronsLibTransmogContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererContextMixin {
    @Inject(method="render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at=@At("HEAD"))
    private void capture(LivingEntity e, float y, float t, PoseStack ps, MultiBufferSource bs, int l, CallbackInfo ci){ IronsLibTransmogContext.set(e); }
    @Inject(method="render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at=@At("RETURN"))
    private void clear(LivingEntity e, float y, float t, PoseStack ps, MultiBufferSource bs, int l, CallbackInfo ci){ IronsLibTransmogContext.clear(); }
}
