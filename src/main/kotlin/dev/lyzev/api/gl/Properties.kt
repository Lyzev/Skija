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

import java.util.*

/**
 * Represents the OpenGL state.
 *
 * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L168-L192)
 * and modified to fit the project's codebase.
 */
class Properties {

    val lastActiveTexture = IntArray(1)
    val lastProgram = IntArray(1)
    val lastTexture = IntArray(1)
    val lastSampler = IntArray(1)
    val lastArrayBuffer = IntArray(1)
    val lastVertexArrayObject = IntArray(1)
    val lastPolygonMode = IntArray(2)
    val lastViewport = IntArray(4)
    val lastScissorBox = IntArray(4)
    val lastBlendSrcRgb = IntArray(1)
    val lastBlendDstRgb = IntArray(1)
    val lastBlendSrcAlpha = IntArray(1)
    val lastBlendDstAlpha = IntArray(1)
    val lastBlendEquationRgb = IntArray(1)
    val lastBlendEquationAlpha = IntArray(1)

    val lastPixelUnpackBufferBinding = IntArray(1)
    val lastUnpackAlignment = IntArray(1)
    val lastUnpackRowLength = IntArray(1)
    val lastUnpackSkipPixels = IntArray(1)
    val lastUnpackSkipRows = IntArray(1)
    val lastPackSwapBytes = IntArray(1)
    val lastPackLsbFirst = IntArray(1)
    val lastPackRowLength = IntArray(1)
    val lastPackImageHeight = IntArray(1)
    val lastPackSkipPixels = IntArray(1)
    val lastPackSkipRows = IntArray(1)
    val lastPackSkipImages = IntArray(1)
    val lastPackAlignment = IntArray(1)
    val lastUnpackSwapBytes = IntArray(1)
    val lastUnpackLsbFirst = IntArray(1)
    val lastUnpackImageHeight = IntArray(1)
    val lastUnpackSkipImages = IntArray(1)

    private val flags = BitSet(7)

    var lastEnableBlend
        get() = flags[0]
        set(value) {
            flags[0] = value
        }
    var lastEnableCullFace
        get() = flags[1]
        set(value) {
            flags[1] = value
        }
    var lastEnableDepthTest
        get() = flags[2]
        set(value) {
            flags[2] = value
        }
    var lastEnableStencilTest
        get() = flags[3]
        set(value) {
            flags[3] = value
        }
    var lastEnableScissorTest
        get() = flags[4]
        set(value) {
            flags[4] = value
        }
    var lastEnablePrimitiveRestart
        get() = flags[5]
        set(value) {
            flags[5] = value
        }

    var lastDepthMask
        get() = flags[6]
        set(value) {
            flags[6] = value
        }
}
