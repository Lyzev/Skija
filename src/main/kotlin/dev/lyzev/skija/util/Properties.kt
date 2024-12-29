/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.util

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

    val lastFramebuffer = IntArray(1)
    val lastDrawBuffer = IntArray(1)
    val lastReadBuffer = IntArray(1)
    val lastDepthFunc = IntArray(1)
    val lastStencilFunc = IntArray(1)
    val lastStencilFail = IntArray(1)
    val lastStencilPassDepthFail = IntArray(1)
    val lastStencilPassDepthPass = IntArray(1)

    var lastDepthMask = false
    val lastStencilMask = IntArray(1)
    val lastCullFaceMode = IntArray(1)
    val lastFrontFace = IntArray(1)
}
