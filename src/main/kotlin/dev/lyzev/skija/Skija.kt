/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija

import com.mojang.blaze3d.systems.RenderSystem
import dev.lyzev.skija.util.States
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

        canvas!!.drawRect(Rect.makeXYWH(10f, 10f, 400f, 200f), Paint().setColor(0x90FF0000.toInt()))
        canvas!!.drawRectShadow(Rect.makeXYWH(10f, 10f, 400f, 200f), 5f, 5f, 10f, 0x90FFFFFF.toInt())

        context!!.flush()

        States.pop()
    }
}
