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

package dev.lyzev.skija

import com.mojang.blaze3d.systems.RenderSystem
import dev.lyzev.skija.util.gl.state.States
import dev.lyzev.skija.util.skija.ImageHelper
import io.github.humbleui.skija.*
import io.github.humbleui.types.Rect
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient


object Skija : ClientModInitializer {

    val mc = MinecraftClient.getInstance()

    override fun onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
    }

    var context: DirectContext? = null
    var renderTarget: BackendRenderTarget? = null
    var surface: Surface? = null
    var canvas: Canvas? = null
    var dpi = 1f

    fun initSkia() {
        if (context == null) {
            context = DirectContext.makeGL()
        }

        surface?.close()
        renderTarget?.close()

        renderTarget = BackendRenderTarget.makeGL(
            (mc.window.framebufferWidth * dpi).toInt(),
            (mc.window.framebufferHeight * dpi).toInt(),
            0,
            8,
            0,
            FramebufferFormat.GR_GL_RGBA8
        )

        surface = Surface.wrapBackendRenderTarget(
            context!!, renderTarget!!, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.getSRGB()
        )

        canvas = surface!!.canvas
    }

    fun draw() {
        States.push()

        RenderSystem.clearColor(0f, 0f, 0f, 0f)

        context!!.resetGLAll()

        val textureImage = ImageHelper.getMinecraftAsImage(
            context!!,
            mc.framebuffer.colorAttachment,
            mc.framebuffer.textureWidth,
            mc.framebuffer.textureHeight,
            alpha = false
        )

        val paint = Paint().apply {
            imageFilter = ImageFilter.makeBlur(20f, 20f, FilterTileMode.REPEAT)
        }

        val rect = Rect.makeXYWH(20f, 20f, 400f, 200f)
        canvas!!.save()
        canvas!!.clipRect(rect, ClipMode.INTERSECT)
        canvas!!.drawImage(textureImage, 0f, 0f, paint)
        canvas!!.restore()

        surface!!.flushAndSubmit()

        States.pop()
    }
}
