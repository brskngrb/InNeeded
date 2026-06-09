package com.brskng.optimized.mixin.ironslib;
import com.brskng.optimized.compat.IronsLibTransmogContext;
import org.spongepowered.asm.mixin.*; import org.spongepowered.asm.mixin.injection.*; import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Pseudo @Mixin(targets="io.redspace.ironslib.patreon.transmog.TransmogClientHandler", remap=false)
public abstract class TransmogClientHandlerMixin {
    @Inject(method="setTransmogRenderActive(Z)V", at=@At("HEAD"), cancellable=true, remap=false)
    private static void suppress(boolean active, CallbackInfo ci){ if(active && IronsLibTransmogContext.shouldSuppressTransmogActivation()) ci.cancel(); }
}
