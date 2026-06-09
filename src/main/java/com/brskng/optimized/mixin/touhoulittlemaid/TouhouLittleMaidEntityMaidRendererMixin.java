package com.brskng.optimized.mixin.touhoulittlemaid;
import com.brskng.optimized.compat.TouhouLittleMaidLodHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo @Mixin(targets="com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer", remap=false)
public abstract class TouhouLittleMaidEntityMaidRendererMixin {
    @Inject(method="render(Lnet/minecraft/world/entity/Mob;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at=@At("HEAD"), cancellable=true)
    private void hardCull(Mob entity, float y, float t, PoseStack ps, MultiBufferSource bs, int l, CallbackInfo ci){ if(TouhouLittleMaidLodHelper.shouldCull(entity)) ci.cancel(); }
}
