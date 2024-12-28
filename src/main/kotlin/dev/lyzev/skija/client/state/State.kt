package dev.lyzev.skija.client.state

import org.lwjgl.opengl.*

class State {
    private val pixelStores = HashMap<PixelStoreParameter, Int>()
    private var lastActiveTexture = 0
    private var lastProgram = 0
    private var lastSampler = 0
    private var lastVertexArray = 0
    private var lastArrayBuffer = 0

    private var lastBlendSrcRgb = 0
    private var lastBlendDstRgb = 0
    private var lastBlendSrcAlpha = 0
    private var lastBlendDstAlpha = 0
    private var lastBlendEquationRgb = 0
    private var lastBlendEquationAlpha = 0

    /**
     * Backs up the current OpenGL state.
     */
    fun backupCurrentState() {
        lastActiveTexture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE)
        lastProgram = GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)
        lastSampler = GL11.glGetInteger(GL33.GL_SAMPLER_BINDING)
        lastArrayBuffer = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING)
        lastVertexArray = GL11.glGetInteger(GL30.GL_VERTEX_ARRAY_BINDING)

        for (parameter in PixelStoreParameter.entries) {
            pixelStores[parameter] = GL11.glGetInteger(parameter.value)
        }

        lastBlendSrcRgb = GL11.glGetInteger(GL14.GL_BLEND_SRC_RGB)
        lastBlendDstRgb = GL11.glGetInteger(GL14.GL_BLEND_DST_RGB)
        lastBlendSrcAlpha = GL11.glGetInteger(GL14.GL_BLEND_SRC_ALPHA)
        lastBlendDstAlpha = GL11.glGetInteger(GL14.GL_BLEND_DST_ALPHA)
        lastBlendEquationRgb = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_RGB)
        lastBlendEquationAlpha = GL11.glGetInteger(GL20.GL_BLEND_EQUATION_ALPHA)
    }

    /**
     * Restores the previous OpenGL state.
     */
    fun restorePreviousState() {
        GL20.glUseProgram(lastProgram)
        GL33.glBindSampler(0, lastSampler)
        GL13.glActiveTexture(lastActiveTexture)
        GL30.glBindVertexArray(lastVertexArray)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, lastArrayBuffer)
        GL20.glBlendEquationSeparate(lastBlendEquationRgb, lastBlendEquationAlpha)
        GL14.glBlendFuncSeparate(lastBlendSrcRgb, lastBlendDstRgb, lastBlendSrcAlpha, lastBlendDstAlpha)

        for (parameter in PixelStoreParameter.entries) {
            GL11.glPixelStorei(parameter.value, pixelStores[parameter]!!)
        }
    }
}
