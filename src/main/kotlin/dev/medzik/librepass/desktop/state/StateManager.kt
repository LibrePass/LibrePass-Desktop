package dev.medzik.librepass.desktop.state

import dev.medzik.librepass.desktop.gui.Controller
import javafx.scene.Scene
import java.io.IOException
import java.util.*


object StateManager {
    private var scene: Scene? = null
    private var currentState: State? = null

    fun init(scene: Scene?,resources: ResourceBundle) {
        StateManager.scene = scene
        for (state in State.values())
            state.load(resources)
    }

    fun getState(state: State): State {
        return state
    }

    fun applyState(state: State) {
        currentState = state
        scene!!.root = state.rootPane

        val controller = state.getController<Any>()
        if (controller is Controller)
            controller.onStart()
    }

    fun setState(state: State) {
        applyState(getState(state))
    }

    fun getCurrentState(): State? {
        return currentState
    }
}