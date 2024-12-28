package dev.lyzev.skija.client.state

import java.util.*

/**
 * UIState is a utility class that manages OpenGL state preservation.
 * It allows saving and restoring of various OpenGL states to ensure
 * that rendering operations do not interfere with each other.
 *
 * @author quantamyt
 *
 * This code is released under the Creative Commons Attribution 4.0 International License (CC BY 4.0).
 * You are free to share and adapt this code, provided appropriate credit is given to the original author.
 * For more details, visit: [Creative Commons](https://creativecommons.org/licenses/by/4.0/deed.en)
 */
object UIState {
    // Stack to manage multiple UI states
    private val stateStack = Stack<State>()

    /**
     * Backs up the current OpenGL state and pushes it onto the stack.
     */
    fun backup() {
        val currentState = State()
        currentState.backupCurrentState()
        stateStack.push(currentState)
    }

    /**
     * Restores the most recent OpenGL state from the stack.
     */
    fun restore() {
        if (!stateStack.isEmpty()) {
            val previousState = stateStack.pop()
            previousState.restorePreviousState()
        }
    }
}
