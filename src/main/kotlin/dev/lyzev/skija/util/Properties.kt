/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.util

import org.lwjgl.opengl.GL30


class Properties {
    val pixelStores = HashMap<PixelStore, Int>()

    val lastActiveTexture = IntArray(1)

    val lastTextures = IntArray(32) { -1 }

    val lastProgram = IntArray(1)
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

    val lastDepthRange = DoubleArray(2)
    val lastLineWidth = FloatArray(1)
    val lastPointSize = FloatArray(1)
    val lastPolygonOffsetFactor = FloatArray(1)
    val lastPolygonOffsetUnits = FloatArray(1)

    var lastColorWriteMask = IntArray(4)
    var lastEnableDepthClamp = false
    var lastEnableSampleCoverage = false
    var lastEnableSampleAlphaToCoverage = false

    var lastEnableMultisample = false
    var lastEnableSampleMask = false
    var lastEnableTextureCubeMapSeamless = false
    var lastEnableFramebufferSRGB = false

    val lastSamplers = IntArray(32) { -1 }
}
