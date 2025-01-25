/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.lyzev.skija.Skija;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @Inject(method = "flipFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;clear()V", shift = At.Shift.AFTER))
    private static void flipFrame(long window, TracyFrameCapturer capturer, CallbackInfo ci) {
        Skija.INSTANCE.draw();
    }
}
