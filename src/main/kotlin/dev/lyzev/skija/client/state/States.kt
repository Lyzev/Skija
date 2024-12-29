/*
 * Copyright (c) 2024. Lyzev
 * All rights reserved.
 */

package dev.lyzev.skija.client.state

import java.util.*

object States {


    private val states = Stack<State>()

    fun push() {
        val currentState = State()
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
}
