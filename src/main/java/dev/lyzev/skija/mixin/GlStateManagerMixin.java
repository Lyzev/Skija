/*
 * Copyright (c) 2025. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.lyzev.skija.util.States;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlStateManager.class)
public class GlStateManagerMixin {

    @Inject(method = "_genTexture", at = @At("RETURN"), remap = false)
    private static void genTexture(CallbackInfoReturnable<Integer> cir) {
        States.INSTANCE.getTextures().push(cir.getReturnValue());
    }
}
