/*
 * This file is part of https://github.com/Lyzev/Skija.
 *
 * Copyright (c) 2024-2025. Lyzev
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

import org.lwjgl.opengl.GL30.*
import java.util.*

/**
 * Stores and restores OpenGL states.
 */
object States {

    private val glVersion: Int;

    private val states = Stack<State>()

    fun push() {
        val currentState = State(glVersion)
        currentState.push()
        states += currentState
    }

    fun pop() {
        if (states.isEmpty()) {
            throw IllegalStateException("No state to restore.")
        }
        val state = states.pop()
        state.pop()
    }

    /**
     * Gets the current OpenGL version.
     *
     * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L250-L254)
     * and modified to fit the project's codebase.
     *
     * The original code was licensed under the MIT License. The original license is included in the root of this project
     * under the file "LICENSE-IMGUI-JAVA".
     *
     * As part of this project, this code is now distributed under the AGPLv3 license.
     */
    init {
        val major = IntArray(1)
        val minor = IntArray(1)
        glGetIntegerv(GL_MAJOR_VERSION, major)
        glGetIntegerv(GL_MINOR_VERSION, minor)
        glVersion = major[0] * 100 + minor[0] * 10
    }
}
