package dev.lyzev.skija.client.state

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL12

/**
 * Enum for pixel store parameters used in OpenGL.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: [Creative Commons](https://creativecommons.org/licenses/by/4.0/deed.en)
 */
enum class PixelStoreParameter(val value: Int) {
    PACK_SWAP_BYTES(GL11.GL_PACK_SWAP_BYTES),
    PACK_LSB_FIRST(GL11.GL_PACK_LSB_FIRST),
    PACK_ROW_LENGTH(GL11.GL_PACK_ROW_LENGTH),
    PACK_IMAGE_HEIGHT(GL12.GL_PACK_IMAGE_HEIGHT),
    PACK_SKIP_PIXELS(GL11.GL_PACK_SKIP_PIXELS),
    PACK_SKIP_ROWS(GL11.GL_PACK_SKIP_ROWS),
    PACK_SKIP_IMAGES(GL12.GL_PACK_SKIP_IMAGES),
    PACK_ALIGNMENT(GL11.GL_PACK_ALIGNMENT),
    UNPACK_SWAP_BYTES(GL11.GL_UNPACK_SWAP_BYTES),
    UNPACK_LSB_FIRST(GL11.GL_UNPACK_LSB_FIRST),
    UNPACK_ROW_LENGTH(GL11.GL_UNPACK_ROW_LENGTH),
    UNPACK_IMAGE_HEIGHT(GL12.GL_UNPACK_IMAGE_HEIGHT),
    UNPACK_SKIP_PIXELS(GL11.GL_UNPACK_SKIP_PIXELS),
    UNPACK_SKIP_ROWS(GL11.GL_UNPACK_SKIP_ROWS),
    UNPACK_SKIP_IMAGES(GL12.GL_UNPACK_SKIP_IMAGES),
    UNPACK_ALIGNMENT(GL11.GL_UNPACK_ALIGNMENT)
}
