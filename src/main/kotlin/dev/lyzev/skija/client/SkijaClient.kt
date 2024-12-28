package dev.lyzev.skija.client

import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import io.github.humbleui.skija.*
import io.github.humbleui.skija.impl.Library
import io.github.humbleui.skija.impl.Stats
import io.github.humbleui.types.Rect
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL30

object SkijaClient : ClientModInitializer {

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
            if ("false".equals(System.getProperty("skija.staticLoad")))
                Library.load();
            context = DirectContext.makeGL()
        }

        Stats.enabled = true

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
        val previousFbo = GlStateManager._getInteger(GL30.GL_FRAMEBUFFER_BINDING)
        val previousProgram = GlStateManager._getInteger(GL30.GL_CURRENT_PROGRAM)
        val previousActiveTexture = GlStateManager._getInteger(GL13.GL_ACTIVE_TEXTURE)
        val previousTextureBinding = GlStateManager._getInteger(GL11.GL_TEXTURE_BINDING_2D)

        RenderSystem.enableBlend() // Tried to use blend mode to make transparency work, but it doesn't
        RenderSystem.defaultBlendFunc()
        canvas!!.clear(0x80FFFFFF.toInt()) // Transparency somehow doesn't work

        canvas!!.drawImage(
            Image.adoptTextureFrom(
                context,
                mc.framebuffer.colorAttachment,
                GL11.GL_TEXTURE_2D,
                mc.framebuffer.textureWidth,
                mc.framebuffer.textureHeight,
                GL11.GL_RGBA8,
                SurfaceOrigin.BOTTOM_LEFT,
                ColorType.RGBA_8888
            ), 0f, 0f
        )

        val paint = Paint().setColor(0x80FFFFFF.toInt())
        canvas!!.drawRect(Rect.makeLTRB(10f, 10f, mc.window.framebufferWidth - 100f, mc.window.framebufferHeight - 100f), paint);

        context!!.flush()
        RenderSystem.disableBlend()

        GlStateManager._glBindFramebuffer(GL30.GL_FRAMEBUFFER, previousFbo)
        GlStateManager._glUseProgram(previousProgram)
        GlStateManager._activeTexture(previousActiveTexture)
        GlStateManager._bindTexture(previousTextureBinding)
    }
}
