package dev.medzik.librepass.desktop

import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.application.Application
import javafx.application.HostServices
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage
import java.util.*

class App : Application() {
    companion object {
        lateinit var hostServices: HostServices
    }

    override fun start(stage: Stage) {
        Config.init()

        val scene = Scene(Pane(), 1024.0, 576.0)

        stage.title = "LibrePass"
        stage.scene = scene
        stage.icons.add(Image(App::class.java.getResourceAsStream("/img/logo.png")))
        App.hostServices = hostServices!!

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
