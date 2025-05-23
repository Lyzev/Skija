/*
 * This file is part of https://github.com/Lyzev/Skija.
 *
 * Copyright (c) 2024-2025. Lyzev
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

import dev.lyzev.api.event.EventSkiaDraw
import dev.lyzev.api.event.EventSkiaInit
import net.minecraft.client.util.Window
import net.minecraft.client.util.tracy.TracyFrameCapturer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * Mixin class that injects the initialization of Skija into the onFramebufferSizeChanged method of Window.
 */
@Mixin(Window::class)
class WindowMixin {

    /**
     * Injects the initialization of Skija into the onFramebufferSizeChanged method of Window.
     * This is done to ensure that Skija is reinitialized after the window has been resized.
     */
    @Inject(method = ["onFramebufferSizeChanged"], at = [At("RETURN")])
    private fun onFramebufferSizeChanged(window: Long, width: Int, height: Int, ci: CallbackInfo) {
        EventSkiaInit(if (width > 0) width else 1, if (height > 0) height else 1).fire()
    }

    @Inject(method = ["swapBuffers"], at = [At("HEAD")])
    private fun onSwapBuffers(capturer: TracyFrameCapturer, ci: CallbackInfo) {
        EventSkiaDraw.fire()
    }
}
