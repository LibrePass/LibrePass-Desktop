package dev.medzik.librepass.desktop

import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.util.*

class App : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(Pane(), 640.0, 480.0)
        scene.stylesheets.add(Objects.requireNonNull(App::class.java.getResource("/style/light/light.css")).toExternalForm())
        stage.title = "LibrePass"
        stage.scene = scene

        val resources: ResourceBundle = ResourceBundle.getBundle("/locales/locale", Locale.getDefault())

        StateManager.init(scene, resources)

        stage.show()

        StateManager.setState(State.WELCOME)
    }
}

fun main() {
    Application.launch(App::class.java)
}
