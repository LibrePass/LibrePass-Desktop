package dev.medzik.librepass.desktop

import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.util.*

class App : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(Pane(), 640.0, 480.0)

        stage.title = "LibrePass"t co
        stage.scene = scene
        stage.icons.add(Image(App::class.java.getResourceAsStream("/img/logo.png")))

        val resources: ResourceBundle = ResourceBundle.getBundle("/locales/locale", Locale.getDefault())

        StateManager.init(scene, resources)
        StyleManager.init()
        StyleManager.trackScene(scene)
        StyleManager.reloadStyle()

        stage.show()

        StateManager.setState(State.WELCOME)
    }
}

fun main() {
    Application.launch(App::class.java)
}
