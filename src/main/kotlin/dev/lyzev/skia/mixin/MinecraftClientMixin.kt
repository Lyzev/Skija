/*
 * This file is part of https://github.com/Lyzev/Skija.
 *
 * Copyright (c) 2025. Lyzev
 *
 * Skija is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License, or
 * (at your option) any later version.
 *
 * Skija is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Skija. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.lyzev.skia.mixin

import dev.lyzev.api.event.EventSkiaInit
import dev.lyzev.api.skia.SkiaImplMc
import dev.lyzev.skia.HudExample
import net.minecraft.client.MinecraftClient
import net.minecraft.client.RunArgs
import org.lwjgl.glfw.GLFW
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(MinecraftClient::class)
class MinecraftClientMixin {

    @Inject(method = ["<init>"], at = [At("RETURN")])
    private fun onInit(
        args: RunArgs,
        ci: CallbackInfo
    ) {
        HudExample // Initialize the example (you can initialize your own anywhere you want)

        // Leave this code as is
        SkiaImplMc
        val width = IntArray(1)
        val height = IntArray(1)
        GLFW.glfwGetFramebufferSize(MinecraftClient.getInstance().window.handle, width, height)
        EventSkiaInit(if (width[0] > 0) width[0] else 1, if (height[0] > 0) height[0] else 1).fire()
    }
}
