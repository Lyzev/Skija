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

package dev.lyzev.skija.util.skija

import com.mojang.blaze3d.systems.RenderSystem
import io.github.humbleui.skija.ColorType
import io.github.humbleui.skija.DirectContext
import io.github.humbleui.skija.Image
import io.github.humbleui.skija.SurfaceOrigin
import net.minecraft.client.MinecraftClient
import org.lwjgl.opengl.GL11

object ImageHelper {

    private val mc = MinecraftClient.getInstance()

    private val textures = mutableMapOf<Int, Image>()

    fun getMinecraftAsImage(
        context: DirectContext,
        tex: Int,
        width: Int,
        height: Int,
        origin: SurfaceOrigin = SurfaceOrigin.BOTTOM_LEFT,
        alpha: Boolean = true
    ): Image {
        RenderSystem.bindTexture(tex)
        val img = textures.getOrPut(tex) {
            Image.adoptTextureFrom(
                context,
                mc.framebuffer.colorAttachment,
                GL11.GL_TEXTURE_2D,
                width,
                height,
                GL11.GL_RGBA8,
                origin,
                if (alpha) ColorType.RGBA_8888
                else ColorType.RGB_888X
            )
        }
        if (img.width != width || img.height != height) {
            textures[tex] = Image.adoptTextureFrom(
                context,
                mc.framebuffer.colorAttachment,
                GL11.GL_TEXTURE_2D,
                width,
                height,
                GL11.GL_RGBA8,
                origin,
                if (alpha) ColorType.RGBA_8888
                else ColorType.RGB_888X
            )
            return textures[tex]!!
        }
        return img
    }
}
