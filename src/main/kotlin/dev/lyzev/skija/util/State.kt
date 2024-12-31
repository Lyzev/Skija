/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.util

import org.lwjgl.opengl.GL40.*

class State {

    private val props = Properties()

    fun push() {
        // Save active texture state
        glGetIntegerv(GL_ACTIVE_TEXTURE, props.lastActiveTexture)

        // Save binding of all 2D textures from GL_TEXTURE0 to GL_TEXTURE31
        for (i in GL_TEXTURE0..GL_TEXTURE31) {
            glActiveTexture(i)
            props.lastTextures[i - GL_TEXTURE0] = glGetInteger(GL_TEXTURE_BINDING_2D)
        }

        // Save the currently active shader program
        glGetIntegerv(GL_CURRENT_PROGRAM, props.lastProgram)

        // Save sampler state for newer OpenGL versions
        if (VERSION >= 330) {
            glGetIntegerv(GL_SAMPLER_BINDING, props.lastSampler)
        }

        // Save buffer and vertex array bindings
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, props.lastArrayBuffer)
        glGetIntegerv(GL_VERTEX_ARRAY_BINDING, props.lastVertexArrayObject)

        // Save polygon mode for OpenGL versions 200 and above
        if (VERSION >= 200) {
            glGetIntegerv(GL_POLYGON_MODE, props.lastPolygonMode)
        }

        // Save viewport and scissor box state
        glGetIntegerv(GL_VIEWPORT, props.lastViewport)
        glGetIntegerv(GL_SCISSOR_BOX, props.lastScissorBox)

        // Save blending parameters (source, destination, and equations)
        glGetIntegerv(GL_BLEND_SRC_RGB, props.lastBlendSrcRgb)
        glGetIntegerv(GL_BLEND_DST_RGB, props.lastBlendDstRgb)
        glGetIntegerv(GL_BLEND_SRC_ALPHA, props.lastBlendSrcAlpha)
        glGetIntegerv(GL_BLEND_DST_ALPHA, props.lastBlendDstAlpha)
        glGetIntegerv(GL_BLEND_EQUATION_RGB, props.lastBlendEquationRgb)
        glGetIntegerv(GL_BLEND_EQUATION_ALPHA, props.lastBlendEquationAlpha)

        // Save enabling states for various OpenGL tests
        saveEnableStates()

        // Save framebuffer binding and draw/read buffers
        glGetIntegerv(GL_FRAMEBUFFER_BINDING, props.lastFramebuffer)
        glGetIntegerv(GL_DRAW_BUFFER, props.lastDrawBuffer)
        glGetIntegerv(GL_READ_BUFFER, props.lastReadBuffer)

        // Save depth and stencil states (function, mask, and stencil operations)
        saveDepthStencilStates()

        // Save additional OpenGL states like depth range, line width, point size, and polygon offset
        saveDepthRangeAndOtherStates()

        // Save pixel store settings (alignments, etc.)
        savePixelStoreStates()

        // Save the color write mask state
        saveColorWriteMask()

        // Save newer OpenGL states for version 300 and above
        saveNewerOpenGLStates()

        saveAdditionalStates()
    }

    private fun saveEnableStates() {
        // Save enable states for various OpenGL features
        props.lastEnableBlend = glIsEnabled(GL_BLEND)
        props.lastEnableCullFace = glIsEnabled(GL_CULL_FACE)
        props.lastEnableDepthTest = glIsEnabled(GL_DEPTH_TEST)
        props.lastEnableStencilTest = glIsEnabled(GL_STENCIL_TEST)
        props.lastEnableScissorTest = glIsEnabled(GL_SCISSOR_TEST)
        if (VERSION >= 310) {
            props.lastEnablePrimitiveRestart = glIsEnabled(GL_PRIMITIVE_RESTART)
        }
    }

    private fun saveDepthStencilStates() {
        // Save depth and stencil states
        glGetIntegerv(GL_DEPTH_FUNC, props.lastDepthFunc)
        glGetIntegerv(GL_STENCIL_FUNC, props.lastStencilFunc)
        glGetIntegerv(GL_STENCIL_FAIL, props.lastStencilFail)
        glGetIntegerv(GL_STENCIL_PASS_DEPTH_FAIL, props.lastStencilPassDepthFail)
        glGetIntegerv(GL_STENCIL_PASS_DEPTH_PASS, props.lastStencilPassDepthPass)
        props.lastDepthMask = glGetBoolean(GL_DEPTH_WRITEMASK)
        glGetIntegerv(GL_STENCIL_WRITEMASK, props.lastStencilMask)
        glGetIntegerv(GL_CULL_FACE_MODE, props.lastCullFaceMode)
        glGetIntegerv(GL_FRONT_FACE, props.lastFrontFace)
    }

    private fun saveDepthRangeAndOtherStates() {
        // Save depth range, line width, point size, and polygon offset
        glGetDoublev(GL_DEPTH_RANGE, props.lastDepthRange)
        glGetFloatv(GL_LINE_WIDTH, props.lastLineWidth)
        glGetFloatv(GL_POINT_SIZE, props.lastPointSize)
        glGetFloatv(GL_POLYGON_OFFSET_FACTOR, props.lastPolygonOffsetFactor)
        glGetFloatv(GL_POLYGON_OFFSET_UNITS, props.lastPolygonOffsetUnits)
    }

    private fun savePixelStoreStates() {
        // Save pixel store states (alignment, etc.)
        for (parameter in PixelStore.values()) {
            props.pixelStores[parameter] = glGetInteger(parameter.value)
        }
    }

    private fun saveColorWriteMask() {
        // Save color write mask state
        val colorWriteMask = IntArray(4)
        glGetIntegerv(GL_COLOR_WRITEMASK, colorWriteMask)
        props.lastColorWriteMask = colorWriteMask
    }

    private fun saveNewerOpenGLStates() {
        // Save additional OpenGL states for newer versions
        if (VERSION >= 300) {
            props.lastEnableDepthClamp = glIsEnabled(GL_DEPTH_CLAMP)
        }

        if (VERSION >= 130) {
            props.lastEnableSampleCoverage = glIsEnabled(GL_SAMPLE_COVERAGE)
            props.lastEnableSampleAlphaToCoverage = glIsEnabled(GL_SAMPLE_ALPHA_TO_COVERAGE)
        }
    }

    private fun saveAdditionalStates() {
        if (VERSION >= 300) {
            props.lastEnableMultisample = glIsEnabled(GL_MULTISAMPLE)
            props.lastEnableSampleMask = glIsEnabled(GL_SAMPLE_MASK)
            props.lastEnableTextureCubeMapSeamless = glIsEnabled(GL_TEXTURE_CUBE_MAP_SEAMLESS)
            props.lastEnableFramebufferSRGB = glIsEnabled(GL_FRAMEBUFFER_SRGB)
        }
    }

    fun pop() {
        // Restore the shader program
        glUseProgram(props.lastProgram[0])
        if (VERSION >= 330) {
            glBindSampler(0, props.lastSampler[0])
        }

        // Restore textures from GL_TEXTURE0 to GL_TEXTURE31
        restoreTextures()

        // Restore the active texture
        glActiveTexture(props.lastActiveTexture[0])

        // Restore vertex array and buffer bindings
        glBindVertexArray(props.lastVertexArrayObject[0])
        glBindBuffer(GL_ARRAY_BUFFER, props.lastArrayBuffer[0])

        // Restore blending states (source, destination, and equations)
        restoreBlending()

        // Restore enable states for various OpenGL features
        restoreEnableStates()

        // Restore polygon mode (for versions 200 and above)
        if (VERSION >= 200) {
            glPolygonMode(GL_FRONT_AND_BACK, props.lastPolygonMode[0])
        }

        // Restore viewport and scissor box state
        glViewport(props.lastViewport[0], props.lastViewport[1], props.lastViewport[2], props.lastViewport[3])
        glScissor(props.lastScissorBox[0], props.lastScissorBox[1], props.lastScissorBox[2], props.lastScissorBox[3])

        // Restore framebuffer binding and draw/read buffers
        glBindFramebuffer(GL_FRAMEBUFFER, props.lastFramebuffer[0])
        glDrawBuffer(props.lastDrawBuffer[0])
        glReadBuffer(props.lastReadBuffer[0])

        // Restore depth and stencil states
        restoreDepthStencilStates()

        // Restore depth mask and stencil mask
        glDepthMask(props.lastDepthMask)
        glStencilMask(props.lastStencilMask[0])
        glCullFace(props.lastCullFaceMode[0])
        glFrontFace(props.lastFrontFace[0])

        // Restore depth range
        glDepthRange(props.lastDepthRange[0], props.lastDepthRange[1])
        glLineWidth(props.lastLineWidth[0])
        glPointSize(props.lastPointSize[0])
        glPolygonOffset(props.lastPolygonOffsetFactor[0], props.lastPolygonOffsetUnits[0])

        // Restore pixel store parameters
        restorePixelStoreStates()

        // Restore color write mask
        glColorMask(props.lastColorWriteMask[0] != 0, props.lastColorWriteMask[1] != 0, props.lastColorWriteMask[2] != 0, props.lastColorWriteMask[3] != 0)

        // Restore additional states for newer OpenGL versions
        restoreNewerOpenGLStates()

        restoreAdditionalStates()
    }

    private fun restoreTextures() {
        // Restore texture bindings for all textures
        for (i in GL_TEXTURE0..GL_TEXTURE31) {
            glActiveTexture(i)
            glBindTexture(GL_TEXTURE_2D, props.lastTextures[i - GL_TEXTURE0])
        }
    }

    private fun restoreBlending() {
        // Restore blend equation and function settings
        glBlendEquationSeparate(props.lastBlendEquationRgb[0], props.lastBlendEquationAlpha[0])
        glBlendFuncSeparate(props.lastBlendSrcRgb[0], props.lastBlendDstRgb[0], props.lastBlendSrcAlpha[0], props.lastBlendDstAlpha[0])
        if (props.lastEnableBlend) glEnable(GL_BLEND)
        else glDisable(GL_BLEND)
    }

    private fun restoreEnableStates() {
        // Restore enable states for various OpenGL features
        if (props.lastEnableCullFace) glEnable(GL_CULL_FACE)
        else glDisable(GL_CULL_FACE)
        if (props.lastEnableDepthTest) glEnable(GL_DEPTH_TEST)
        else glDisable(GL_DEPTH_TEST)
        if (props.lastEnableStencilTest) glEnable(GL_STENCIL_TEST)
        else glDisable(GL_STENCIL_TEST)
        if (props.lastEnableScissorTest) glEnable(GL_SCISSOR_TEST)
        else glDisable(GL_SCISSOR_TEST)
        if (VERSION >= 310) {
            if (props.lastEnablePrimitiveRestart) glEnable(GL_PRIMITIVE_RESTART)
            else glDisable(GL_PRIMITIVE_RESTART)
        }
    }

    private fun restoreDepthStencilStates() {
        // Restore depth and stencil states (functions and operations)
        glDepthFunc(props.lastDepthFunc[0])
        glStencilFunc(props.lastStencilFunc[0], 0, 0xFF)
        glStencilOp(props.lastStencilFail[0], props.lastStencilPassDepthFail[0], props.lastStencilPassDepthPass[0])
    }

    private fun restorePixelStoreStates() {
        // Restore pixel store states
        for (parameter in PixelStore.values()) {
            glPixelStorei(parameter.value, props.pixelStores[parameter]!!)
        }
    }

    private fun restoreNewerOpenGLStates() {
        // Restore additional OpenGL states for newer versions
        if (VERSION >= 300) {
            if (props.lastEnableDepthClamp) glEnable(GL_DEPTH_CLAMP)
            else glDisable(GL_DEPTH_CLAMP)
        }

        if (VERSION >= 130) {
            if (props.lastEnableSampleCoverage) glEnable(GL_SAMPLE_COVERAGE)
            else glDisable(GL_SAMPLE_COVERAGE)
            if (props.lastEnableSampleAlphaToCoverage) glEnable(GL_SAMPLE_ALPHA_TO_COVERAGE)
            else glDisable(GL_SAMPLE_ALPHA_TO_COVERAGE)
        }
    }

    private fun restoreAdditionalStates() {
        if (VERSION >= 300) {
            if (props.lastEnableMultisample) glEnable(GL_MULTISAMPLE)
            else glDisable(GL_MULTISAMPLE)
            if (props.lastEnableSampleMask) glEnable(GL_SAMPLE_MASK)
            else glDisable(GL_SAMPLE_MASK)
            if (props.lastEnableTextureCubeMapSeamless) glEnable(GL_TEXTURE_CUBE_MAP_SEAMLESS)
            else glDisable(GL_TEXTURE_CUBE_MAP_SEAMLESS)
            if (props.lastEnableFramebufferSRGB) glEnable(GL_FRAMEBUFFER_SRGB)
            else glDisable(GL_FRAMEBUFFER_SRGB)
        }
    }

    companion object {
        val VERSION: Int

        init {
            val major = IntArray(1)
            val minor = IntArray(1)
            glGetIntegerv(GL_MAJOR_VERSION, major)
            glGetIntegerv(GL_MINOR_VERSION, minor)
            VERSION = major[0] * 100 + minor[0] * 10
        }
    }
}
