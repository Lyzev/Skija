/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.mixin;

import dev.lyzev.skija.client.SkijaClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/toast/ToastManager;draw(Lnet/minecraft/client/gui/DrawContext;)V", shift = At.Shift.AFTER))
    private void renderSkija(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
//        SkijaClient.INSTANCE.draw();
    }
}
