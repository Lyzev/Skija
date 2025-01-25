/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija

import com.mojang.blaze3d.systems.RenderSystem
import dev.lyzev.skija.util.skija.ImageHelper
import dev.lyzev.skija.util.gl.state.States
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
