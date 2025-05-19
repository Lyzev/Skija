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

package dev.lyzev.api.skia

import io.github.humbleui.skija.*

/**
 * A helper class for loading fonts.
 */
object FontHelper {

    /**
     * The default font size.
     */
    const val DEFAULT_SIZE = 18f

    /**
     * The root directory for fonts.
     */
    const val ROOT = "assets/skija/fonts/"

    /**
     * The fonts that have been loaded.
     */
    private val fonts by lazy { mutableMapOf<String, Font>() }

    /**
     * The typefaces that have been loaded.
     */
    private val typefaces by lazy { mutableMapOf<String, Typeface>() }

    /**
     * Gets a font by its path and size.
     */
    operator fun get(path: String, size: Float = DEFAULT_SIZE) = fonts.computeIfAbsent("$path:$size") {
        Font(
            loadTypeface(path), size
        ).apply {
            isSubpixel = false
            hinting = FontHinting.NORMAL
            edging = FontEdging.ANTI_ALIAS
        }
    }

    /**
     * Loads a typeface by its path.
     */
    private fun loadTypeface(path: String) = typefaces.computeIfAbsent(path) {
        val typefaceData = javaClass.classLoader.getResourceAsStream("$ROOT$path")?.use { it.readAllBytes() }
        requireNotNull(typefaceData) { "Font not found: $ROOT$path" }
        Typeface.makeFromData(Data.makeFromBytes(typefaceData))
    }
}
