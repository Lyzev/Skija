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
import net.minecraft.client.MinecraftClient

/**
 * This is an example Minecraft implementation that integrates the Skija graphics library to enhance rendering capabilities.
 */
object Skija {

    /**
     * The Minecraft client instance.
     */
    val mc: MinecraftClient = MinecraftClient.getInstance()

    // --- Skia-related fields ---
    private var context: DirectContext? = null
    private var renderTarget: BackendRenderTarget? = null
    private var surface: Surface? = null
    private var canvas: Canvas? = null
    private var dpi = 1f

    /**
     * Initializes Skia.
     */
    fun initSkia() {
        // create context if it doesn't exist
        if (context == null) {
            context = DirectContext.makeGL()
        }

        // close the previous surface and render target if
        surface?.close()
        renderTarget?.close()

        // create a new render target and surface
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

        // set the canvas
        canvas = surface!!.canvas
    }

    /**
     * Draws the Skia scene.
     */
    fun draw() {
        States.push() // save the OpenGL state to ensure that Skia doesn't mess up the rendering

        RenderSystem.clearColor(0f, 0f, 0f, 0f) // clear the color buffer

        context!!.resetGLAll() // reset the OpenGL state to ensure the Minecraft gl state doesn't mess up the rendering

        // as an example, we'll draw a blurred version of the Minecraft scene in the top left corner

        val textureImage = ImageHelper.getMinecraftSceneAsSkijaImage(
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

        surface!!.flushAndSubmit() // submit the surface to the GPU

        States.pop() // restore the OpenGL state to ensure that Minecraft renders properly
    }
}
