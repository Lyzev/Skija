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

package dev.lyzev.skia

import dev.lyzev.api.event.EventListener
import dev.lyzev.api.event.EventSkiaDrawScene
import dev.lyzev.api.event.on
import dev.lyzev.api.setting.settings.toggleable
import dev.lyzev.api.skia.FontHelper
import dev.lyzev.api.skia.ImageHelper
import dev.lyzev.api.skia.WrappedBackendRenderTarget
import io.github.humbleui.skija.*
import io.github.humbleui.types.Rect
import net.minecraft.client.MinecraftClient

object HudExample : EventListener {

    private const val PADDING = 5f
    private val mc by lazy { MinecraftClient.getInstance() }

    var isHidden by toggleable("Hidden")

    private val font by lazy {
        FontHelper["OpenSans-Regular.ttf"]
    }

    private val blur by lazy {
        Paint().apply {
            imageFilter = ImageFilter.makeBlur(20f, 20f, FilterTileMode.REPEAT)
        }
    }

    private val white by lazy {
        Paint().apply {
            color = Color.makeRGB(255, 255, 255)
        }
    }

    private val glow by lazy {
        Paint().apply {
            color = Color.makeRGB(255, 255, 255)
            imageFilter = ImageFilter.makeBlur(2f, 2f, FilterTileMode.DECAL)
        }
    }

    private val dropShadow by lazy {
        Paint().apply {
            color = Color.makeRGB(0, 0, 0)
            imageFilter = ImageFilter.makeBlur(4f, 4f, FilterTileMode.DECAL)
        }
    }

    private fun draw(context: DirectContext, renderTarget: WrappedBackendRenderTarget, canvas: Canvas) {
        val textureImage = ImageHelper[
            context,
            mc.framebuffer.colorAttachment,
            mc.framebuffer.textureWidth,
            mc.framebuffer.textureHeight,
            false
        ]

        val content = "${mc.currentFps} FPS @ ${renderTarget.width}x${renderTarget.height} | lyzev (Бен)"
        val textSize = font.measureText(content)
        val rect = Rect.makeXYWH(PADDING, PADDING, textSize.right + 2 * PADDING, textSize.height + 2 * PADDING)
        with(canvas) {
            save()
            clipRect(rect, ClipMode.INTERSECT)
            drawImage(textureImage, 0f, 0f, blur)
            restore()
            save()
            clipRect(rect, ClipMode.DIFFERENCE)
            drawRect(rect, dropShadow)
            restore()

            drawString(content, 2 * PADDING, PADDING - textSize.top + PADDING, font, glow)
            drawString(content, 2 * PADDING, PADDING - textSize.top + PADDING, font, white)
        }
    }

    override val shouldHandleEvents: Boolean
        get() = !isHidden

    init {
        on<EventSkiaDrawScene> { event ->
            draw(event.context, event.renderTarget, event.canvas)
        }
    }
}
