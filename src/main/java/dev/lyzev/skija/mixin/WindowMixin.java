package dev.lyzev.skija.mixin;

import dev.lyzev.skija.client.SkijaClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(method = "onWindowSizeChanged", at = @At("TAIL"))
    private void onWindowSizeChanged(long window, int width, int height, CallbackInfo ci) {
        SkijaClient.INSTANCE.initSkia();
    }
}
