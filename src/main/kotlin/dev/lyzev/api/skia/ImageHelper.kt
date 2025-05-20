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

package dev.lyzev.api.skia

import com.mojang.blaze3d.opengl.GlConst
import com.mojang.blaze3d.opengl.GlStateManager
import io.github.humbleui.skija.ColorType
import io.github.humbleui.skija.DirectContext
import io.github.humbleui.skija.Image
import io.github.humbleui.skija.SurfaceOrigin
import org.lwjgl.opengl.GL11

/**
 * A helper class for loading images.
 */
object ImageHelper {

    /**
     * The textures that have been loaded.
     */
    private val textures = mutableMapOf<Int, Image>()

    /**
     * Gets the Minecraft scene as a Skija image.
     */
    operator fun get(
        context: DirectContext,
        textureId: Int,
        width: Int,
        height: Int,
        hasAlpha: Boolean = true,
        origin: SurfaceOrigin = SurfaceOrigin.BOTTOM_LEFT
    ): Image {
        require(width > 0 && height > 0) { "Width and height must be positive" }

        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, textureId)
        return textures.getOrPut(textureId) {
            create(context, textureId, width, height, origin, hasAlpha)
        }.apply {
            if (this.width != width || this.height != height) {
                textures[textureId] = create(context, textureId, width, height, origin, hasAlpha)
            }
        }
    }

    /**
     * Creates an image from the given texture.
     */
    private fun create(
        context: DirectContext,
        textureId: Int,
        width: Int,
        height: Int,
        origin: SurfaceOrigin,
        hasAlpha: Boolean
    ) = Image.adoptGLTextureFrom(
        context,
        textureId,
        GL11.GL_TEXTURE_2D,
        width,
        height,
        GL11.GL_RGBA8,
        origin,
        if (hasAlpha) ColorType.RGBA_8888 else ColorType.RGB_888X
    )
}
