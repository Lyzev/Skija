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

package dev.lyzev.skija.util.gl.state

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
     * The original code was licensed under the MIT License. The original license is included in the root of this project
     * under the file "LICENSE-IMGUI-JAVA".
     *
     * As part of this project, this code is now distributed under the AGPLv3 license.
     *
     * @see pop
     */
    fun push() {
        glGetIntegerv(GL_ACTIVE_TEXTURE, props.lastActiveTexture)
        glActiveTexture(GL_TEXTURE0);
        glGetIntegerv(GL_CURRENT_PROGRAM, props.lastProgram)
        glGetIntegerv(GL_TEXTURE_BINDING_2D, props.lastTexture);
        if (glVersion >= 330 || GL.getCapabilities().GL_ARB_sampler_objects) {
            glGetIntegerv(GL_SAMPLER_BINDING, props.lastSampler);
        }
        glGetIntegerv(GL_ARRAY_BUFFER_BINDING, props.lastArrayBuffer)
        glGetIntegerv(GL_VERTEX_ARRAY_BINDING, props.lastVertexArrayObject)
        if (glVersion >= 200) {
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
        if (glVersion >= 310) {
            props.lastEnablePrimitiveRestart = glIsEnabled(GL_PRIMITIVE_RESTART)
        }

        // These states are not saved in the original imgui-java project but are included to address bugs encountered when drawing with Skija.
        props.lastDepthMask = glGetBoolean(GL_DEPTH_WRITEMASK)
    }

    /**
     * Restores the state that was saved with [push].
     *
     * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L500-L532)
     * and modified to fit the project's codebase.
     *
     * The original code was licensed under the MIT License. The original license is included in the root of this project
     * under the file "LICENSE-IMGUI-JAVA".
     *
     * As part of this project, this code is now distributed under the AGPLv3 license.
     *
     * @see push
     */
    fun pop() {
        glUseProgram(props.lastProgram[0])
        glBindTexture(GL_TEXTURE_2D, props.lastTexture[0]);
        if (glVersion >= 330 || GL.getCapabilities().GL_ARB_sampler_objects) {
            glBindSampler(0, props.lastSampler[0]);
        }
        glActiveTexture(props.lastActiveTexture[0])
        glBindVertexArray(props.lastVertexArrayObject[0])
        glBindBuffer(GL_ARRAY_BUFFER, props.lastArrayBuffer[0])
        glBlendEquationSeparate(props.lastBlendEquationRgb[0], props.lastBlendEquationAlpha[0])
        glBlendFuncSeparate(
            props.lastBlendSrcRgb[0], props.lastBlendDstRgb[0], props.lastBlendSrcAlpha[0], props.lastBlendDstAlpha[0]
        )
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
        if (glVersion >= 310) {
            if (props.lastEnablePrimitiveRestart) glEnable(GL_PRIMITIVE_RESTART)
            else glDisable(GL_PRIMITIVE_RESTART)
        }
        if (glVersion >= 200) {
            glPolygonMode(GL_FRONT_AND_BACK, props.lastPolygonMode[0])
        }
        glViewport(props.lastViewport[0], props.lastViewport[1], props.lastViewport[2], props.lastViewport[3])
        glScissor(props.lastScissorBox[0], props.lastScissorBox[1], props.lastScissorBox[2], props.lastScissorBox[3])

        // These states are not restored in the original imgui-java project but are included to address bugs encountered when drawing with Skija.
        glDepthMask(props.lastDepthMask) // This is a workaround for a bug where the text renderer of Minecraft would not render text properly (flickering text). This also fixes the issue that resizing the window would cause the buttons and more to disappear.
    }
}
