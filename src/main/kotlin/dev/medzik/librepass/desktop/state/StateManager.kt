package dev.medzik.librepass.desktop.state

import dev.medzik.librepass.desktop.gui.Controller
import javafx.application.Platform
import javafx.scene.Scene
import java.util.*

object StateManager {
    private lateinit var scene: Scene
    private var currentState: State? = null

    fun init(scene: Scene, resources: ResourceBundle) {
        StateManager.scene = scene
        for (state in State.entries)
            state.load(resources)
    }

    fun getState(state: State): State {
        return state
    }

    fun applyState(state: State) = Platform.runLater {
        if (currentState != null) {
            val currentController = currentState!!.getController<Any>()
            if (currentController is Controller)
                currentController.onStop()
        }

        currentState = state
        scene.root = state.rootPane

        val controller = state.getController<Any>()
        if (controller is Controller)
            controller.onStart()
    }

    fun setState(state: State) =
        applyState(state)
}
