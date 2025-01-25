/*
 * Copyright (c) 2025. Lyzev
 * All rights reserved.
 */
package dev.lyzev.skija.util.gl.state

/**
 * Represents the OpenGL state.
 *
 * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L168-L192)
 * and modified to fit the project's codebase.
 *
 * The original code was licensed under the MIT License. The original license is included in the root of this project
 * under the file "LICENSE-IMGUI-JAVA".
 *
 * As part of this project, this code is now distributed under the AGPLv3 license.
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
    var lastEnableBlend = false
    var lastEnableCullFace = false
    var lastEnableDepthTest = false
    var lastEnableStencilTest = false
    var lastEnableScissorTest = false
    var lastEnablePrimitiveRestart = false

    // These properties are not used in the original imgui-java project.
    var lastDepthMask = false
}
