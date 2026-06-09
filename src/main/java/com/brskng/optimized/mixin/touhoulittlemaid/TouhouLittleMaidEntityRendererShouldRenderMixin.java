package com.brskng.optimized.mixin.touhoulittlemaid;
import com.brskng.optimized.compat.TouhouLittleMaidLodHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EntityRenderer.class)
public abstract class TouhouLittleMaidEntityRendererShouldRenderMixin<T extends Entity> {
    @Inject(method="shouldRender", at=@At("RETURN"), cancellable=true)
    private void limit(T entity, Frustum f, double x, double y, double z, CallbackInfoReturnable<Boolean> cir){
        if(cir.getReturnValue() && TouhouLittleMaidLodHelper.shouldCull(entity)) cir.setReturnValue(false);
    }
}
