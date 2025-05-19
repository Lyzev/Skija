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

package dev.lyzev.api.gl

import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL45.*

/**
 * Represents the OpenGL state.
 */
class State(private val glVersion: Int) {

    /**
     * The properties of the OpenGL state.
     */
    private val props = Properties()

    /**
     * Saves the current OpenGL state.
     *
     * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L398-L425)
     * and modified to fit the project's codebase.
     *
     * @see pop
     */
    fun push(): State {
        with(props) {
            glGetIntegerv(GL_ACTIVE_TEXTURE, lastActiveTexture)
            glActiveTexture(GL_TEXTURE0)
            glGetIntegerv(GL_CURRENT_PROGRAM, lastProgram)
            glGetIntegerv(GL_TEXTURE_BINDING_2D, lastTexture)
            if (glVersion >= 330 || GL.getCapabilities().GL_ARB_sampler_objects) {
                glGetIntegerv(GL_SAMPLER_BINDING, lastSampler)
            }
            glGetIntegerv(GL_ARRAY_BUFFER_BINDING, lastArrayBuffer)
            glGetIntegerv(GL_VERTEX_ARRAY_BINDING, lastVertexArrayObject)
            if (glVersion >= 200) {
                glGetIntegerv(GL_POLYGON_MODE, lastPolygonMode)
            }
            glGetIntegerv(GL_VIEWPORT, lastViewport)
            glGetIntegerv(GL_SCISSOR_BOX, lastScissorBox)
            glGetIntegerv(GL_BLEND_SRC_RGB, lastBlendSrcRgb)
            glGetIntegerv(GL_BLEND_DST_RGB, lastBlendDstRgb)
            glGetIntegerv(GL_BLEND_SRC_ALPHA, lastBlendSrcAlpha)
            glGetIntegerv(GL_BLEND_DST_ALPHA, lastBlendDstAlpha)
            glGetIntegerv(GL_BLEND_EQUATION_RGB, lastBlendEquationRgb)
            glGetIntegerv(GL_BLEND_EQUATION_ALPHA, lastBlendEquationAlpha)
            lastEnableBlend = glIsEnabled(GL_BLEND)
            lastEnableCullFace = glIsEnabled(GL_CULL_FACE)
            lastEnableDepthTest = glIsEnabled(GL_DEPTH_TEST)
            lastEnableStencilTest = glIsEnabled(GL_STENCIL_TEST)
            lastEnableScissorTest = glIsEnabled(GL_SCISSOR_TEST)
            if (glVersion >= 310) {
                lastEnablePrimitiveRestart = glIsEnabled(GL_PRIMITIVE_RESTART)
            }

            // These states are not saved in the original imgui-java project but are included to address bugs encountered when drawing with Skija.
            lastDepthMask = glGetBoolean(GL_DEPTH_WRITEMASK)

            // Save and set pixel store states for Skia
            glGetIntegerv(GL_PIXEL_UNPACK_BUFFER_BINDING, lastPixelUnpackBufferBinding)
            glBindBuffer(GL_PIXEL_UNPACK_BUFFER, 0)

            // Save all pixel store parameters that affect texture uploads
            glGetIntegerv(GL_UNPACK_ALIGNMENT, lastUnpackAlignment)
            glGetIntegerv(GL_UNPACK_ROW_LENGTH, lastUnpackRowLength)
            glGetIntegerv(GL_UNPACK_SKIP_PIXELS, lastUnpackSkipPixels)
            glGetIntegerv(GL_UNPACK_SKIP_ROWS, lastUnpackSkipRows)

            // Set pixel store parameters optimal for font texture uploads
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
            glPixelStorei(GL_UNPACK_ROW_LENGTH, 0)
            glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0)
            glPixelStorei(GL_UNPACK_SKIP_ROWS, 0)
        }
        return this
    }

    /**
     * Restores the state that was saved with [push].
     *
     * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L500-L532)
     * and modified to fit the project's codebase.
     *
     * @see push
     */
    fun pop(): State {
        with(props) {
            glUseProgram(lastProgram[0])
            glBindTexture(GL_TEXTURE_2D, lastTexture[0])
            if (glVersion >= 330 || GL.getCapabilities().GL_ARB_sampler_objects) {
                glBindSampler(0, lastSampler[0])
            }
            glActiveTexture(lastActiveTexture[0])
            glBindVertexArray(lastVertexArrayObject[0])
            glBindBuffer(GL_ARRAY_BUFFER, lastArrayBuffer[0])
            glBlendEquationSeparate(lastBlendEquationRgb[0], lastBlendEquationAlpha[0])
            glBlendFuncSeparate(
                lastBlendSrcRgb[0],
                lastBlendDstRgb[0],
                lastBlendSrcAlpha[0],
                lastBlendDstAlpha[0]
            )
            if (lastEnableBlend) glEnable(GL_BLEND)
            else glDisable(GL_BLEND)
            if (lastEnableCullFace) glEnable(GL_CULL_FACE)
            else glDisable(GL_CULL_FACE)
            if (lastEnableDepthTest) glEnable(GL_DEPTH_TEST)
            else glDisable(GL_DEPTH_TEST)
            if (lastEnableStencilTest) glEnable(GL_STENCIL_TEST)
            else glDisable(GL_STENCIL_TEST)
            if (lastEnableScissorTest) glEnable(GL_SCISSOR_TEST)
            else glDisable(GL_SCISSOR_TEST)
            if (glVersion >= 310) {
                if (lastEnablePrimitiveRestart) glEnable(GL_PRIMITIVE_RESTART)
                else glDisable(GL_PRIMITIVE_RESTART)
            }
            if (glVersion >= 200) {
                glPolygonMode(GL_FRONT_AND_BACK, lastPolygonMode[0])
            }
            glViewport(lastViewport[0], lastViewport[1], lastViewport[2], lastViewport[3])
            glScissor(
                lastScissorBox[0],
                lastScissorBox[1],
                lastScissorBox[2],
                lastScissorBox[3]
            )

            // Restore pixel store states
            glBindBuffer(GL_PIXEL_UNPACK_BUFFER, lastPixelUnpackBufferBinding[0])
            glPixelStorei(GL_UNPACK_ALIGNMENT, lastUnpackAlignment[0])
            glPixelStorei(GL_UNPACK_ROW_LENGTH, lastUnpackRowLength[0])
            glPixelStorei(GL_UNPACK_SKIP_PIXELS, lastUnpackSkipPixels[0])
            glPixelStorei(GL_UNPACK_SKIP_ROWS, lastUnpackSkipRows[0])

            // These states are not restored in the original imgui-java project but are included to address bugs encountered when drawing with Skija.
            glDepthMask(lastDepthMask) // This is a workaround for a bug where the text renderer of Minecraft would not render text properly (flickering text). This also fixes the issue that resizing the window would cause the buttons and more to disappear.
        }
        return this
    }
}
