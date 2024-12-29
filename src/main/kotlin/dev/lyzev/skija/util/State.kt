/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.util

import org.lwjgl.opengl.GL40.*

class State {

    private val props = Properties()

    fun push() {
        glGetIntegerv(GL_ACTIVE_TEXTURE, props.lastActiveTexture)
        glActiveTexture(GL_TEXTURE0)
        glGetIntegerv(GL_CURRENT_PROGRAM, props.lastProgram)
        glGetIntegerv(GL_TEXTURE_BINDING_2D, props.lastTexture)
        if (VERSION >= 330) {
            glGetIntegerv(GL_SAMPLER_BINDING, props.lastSampler)
        }
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, props.lastArrayBuffer)
        glGetIntegerv(GL_VERTEX_ARRAY_BINDING, props.lastVertexArrayObject)
        if (VERSION >= 200) {
            glGetIntegerv(GL_POLYGON_MODE, props.lastPolygonMode)
        }
        glGetIntegerv(GL_VIEWPORT, props.lastViewport)
        glGetIntegerv(GL_SCISSOR_BOX, props.lastScissorBox)
        glGetIntegerv(GL_BLEND_SRC_RGB, props.lastBlendSrcRgb)
        glGetIntegerv(GL_BLEND_DST_RGB, props.lastBlendDstRgb)
        glGetIntegerv(GL_BLEND_SRC_ALPHA, props.lastBlendSrcAlpha)
        glGetIntegerv(GL_BLEND_DST_ALPHA, props.lastBlendDstAlpha)
        glGetIntegerv(GL_BLEND_EQUATION_RGB, props.lastBlendEquationRgb)
        glGetIntegerv(GL_BLEND_EQUATION_ALPHA, props.lastBlendEquationAlpha)
        props.lastEnableBlend = glIsEnabled(GL_BLEND)
        props.lastEnableCullFace = glIsEnabled(GL_CULL_FACE)
        props.lastEnableDepthTest = glIsEnabled(GL_DEPTH_TEST)
        props.lastEnableStencilTest = glIsEnabled(GL_STENCIL_TEST)
        props.lastEnableScissorTest = glIsEnabled(GL_SCISSOR_TEST)
        if (VERSION >= 310) {
            props.lastEnablePrimitiveRestart = glIsEnabled(GL_PRIMITIVE_RESTART)
        }
    }

    fun pop() {
        glUseProgram(props.lastProgram[0])
        glBindTexture(GL_TEXTURE_2D, props.lastTexture[0])
        if (VERSION >= 330) {
            glBindSampler(0, props.lastSampler[0])
        }
        glActiveTexture(props.lastActiveTexture[0])
        glBindVertexArray(props.lastVertexArrayObject[0])
        glBindBuffer(GL_ARRAY_BUFFER, props.lastArrayBuffer[0])
        glBlendEquationSeparate(props.lastBlendEquationRgb[0], props.lastBlendEquationAlpha[0])
        glBlendFuncSeparate(props.lastBlendSrcRgb[0], props.lastBlendDstRgb[0], props.lastBlendSrcAlpha[0], props.lastBlendDstAlpha[0])
        if (props.lastEnableBlend) glEnable(GL_BLEND)
        else glDisable(GL_BLEND)
        if (props.lastEnableCullFace) glEnable(GL_CULL_FACE)
        else glDisable(GL_CULL_FACE)
        if (props.lastEnableDepthTest) glEnable(GL_DEPTH_TEST)
        else glDisable(GL_DEPTH_TEST)
        if (props.lastEnableStencilTest) glEnable(GL_STENCIL_TEST)
        else glDisable(GL_STENCIL_TEST)
        if (props.lastEnableScissorTest) glEnable(GL_SCISSOR_TEST)
        else glDisable(GL_SCISSOR_TEST)
        if (VERSION >= 310) {
            if (props.lastEnablePrimitiveRestart) {
                glEnable(GL_PRIMITIVE_RESTART)
            } else {
                glDisable(GL_PRIMITIVE_RESTART)
            }
        }
        if (VERSION >= 200) {
            glPolygonMode(GL_FRONT_AND_BACK, props.lastPolygonMode[0])
        }
        glViewport(props.lastViewport[0], props.lastViewport[1], props.lastViewport[2], props.lastViewport[3])
        glScissor(props.lastScissorBox[0], props.lastScissorBox[1], props.lastScissorBox[2], props.lastScissorBox[3])
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
