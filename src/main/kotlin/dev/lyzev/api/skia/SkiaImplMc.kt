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

package dev.lyzev.api.skia

import com.mojang.blaze3d.systems.RenderSystem
import dev.lyzev.api.event.*
import dev.lyzev.api.gl.States
import io.github.humbleui.skija.*
import net.minecraft.client.MinecraftClient

/**
 * The Skia implementation for Minecraft.
 */
object SkiaImplMc : EventListener {

    /**
     * The OpenGL backend states to reset.
     * These are needed to ensure that Skia can draw correctly after Minecraft's rendering.
     */
    private val states = arrayOf(
        GLBackendState.BLEND,
        GLBackendState.VERTEX,
        GLBackendState.PIXEL_STORE,
        GLBackendState.TEXTURE_BINDING,
        GLBackendState.MISC
    )

    private var context: DirectContext? = null
    private var renderTarget: WrappedBackendRenderTarget? = null
    private var surface: Surface? = null
    var canvas: Canvas? = null
        private set

    /**
     * Initializes Skia.
     */
    private fun initSkia(width: Int, height: Int) {
        createContext()
        createSurface(width, height)
    }

    /**
     * Creates the Skia context if it doesn't exist.
     */
    private fun createContext() {
        if (context == null) {
            context = DirectContext.makeGL()
        }
    }

    /**
     * Creates the render target and surface.
     */
    private fun createSurface(width: Int, height: Int) {
        surface?.close()
        renderTarget?.close()

        renderTarget = WrappedBackendRenderTarget.makeGL(
            width,
            height,
            0,
            8,
            MinecraftClient.getInstance().framebuffer.fbo,
            FramebufferFormat.GR_GL_RGBA8
        )
        surface = Surface.wrapBackendRenderTarget(
            requireNotNull(context),
            requireNotNull(renderTarget),
            SurfaceOrigin.BOTTOM_LEFT,
            SurfaceColorFormat.RGBA_8888,
            ColorSpace.getSRGB()
        )
        canvas = surface?.canvas
    }

    /**
     * Draws the Skia scene.
     */
    private fun draw() {
        if (context == null || surface == null) return

        States.push()
        RenderSystem.disableCull()
        RenderSystem.clearColor(0f, 0f, 0f, 0f)

        // Use selective state resetting instead of resetGLAll()
        context?.resetGL(*states)

        drawDrawables()

        // Ensure Skija submits all pending operations
        surface?.flushAndSubmit()

        States.pop()
    }

    /**
     * Draws each drawable in the scene.
     */
    private fun drawDrawables() {
        canvas?.let { canvas ->
            context?.let { context ->
                renderTarget?.let { renderTarget ->
                    EventSkiaDrawScene(context, renderTarget, canvas).fire()
                }
            }
        }
    }

    override val shouldHandleEvents = true

    init {
        on<EventSkiaInit> { event ->
            initSkia(event.width, event.height)
        }

        on<EventSkiaDraw> {
            draw()
        }
    }
}
