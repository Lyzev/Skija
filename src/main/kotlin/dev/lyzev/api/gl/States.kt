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

package dev.lyzev.api.gl

import org.lwjgl.opengl.GL30.*
import java.util.*

/**
 * Stores and restores OpenGL states.
 */
object States {

    /**
     * The current OpenGL version.
     */
    private val glVersion: Int

    /**
     * The stack of OpenGL states.
     */
    private val states = Stack<State>()

    /**
     * Pushes the current OpenGL state onto the stack.
     */
    fun push() {
        states += State(glVersion).push()
    }

    /**
     * Pops the last OpenGL state from the stack and restores it.
     */
    fun pop() {
        require(states.isNotEmpty()) { "No state to restore." }
        states.pop().pop()
    }

    /**
     * Gets the current OpenGL version.
     *
     * This code was inspired by [imgui-java](https://github.com/SpaiR/imgui-java/blob/2a605f0d8500f27e13fa1d2b4cf8cadd822789f4/imgui-lwjgl3/src/main/java/imgui/gl3/ImGuiImplGl3.java#L250-L254)
     * and modified to fit the project's codebase.
     */
    init {
        val major = IntArray(1)
        val minor = IntArray(1)
        glGetIntegerv(GL_MAJOR_VERSION, major)
        glGetIntegerv(GL_MINOR_VERSION, minor)
        glVersion = major[0] * 100 + minor[0] * 10
    }
}
