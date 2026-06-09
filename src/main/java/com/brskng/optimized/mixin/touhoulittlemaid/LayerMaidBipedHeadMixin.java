package com.brskng.optimized.mixin.touhoulittlemaid;
import com.brskng.optimized.compat.TouhouLittleMaidLodHelper;
import com.brskng.optimized.config.BrskngsOptimizedClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo @Mixin(targets="com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.layer.LayerMaidBipedHead", remap=false)
public abstract class LayerMaidBipedHeadMixin {
    @Inject(method="render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/Mob;FFFFFF)V", at=@At("HEAD"), cancellable=true)
    private void skipLod(PoseStack ps, MultiBufferSource bs, int l, Mob maid, float a, float b, float c, float d, float e, float f, CallbackInfo ci){
        if(BrskngsOptimizedClientConfig.TLM_LOD_DISABLE_HEAD_LAYER.get() && TouhouLittleMaidLodHelper.shouldUseLod(maid)) ci.cancel();
    }
}
