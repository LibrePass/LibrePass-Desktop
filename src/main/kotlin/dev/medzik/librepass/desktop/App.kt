package dev.medzik.librepass.desktop

import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Settings
import dev.medzik.librepass.desktop.gui.dashboard.DashboardController
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
        lateinit var stage: Stage
    }

    override fun start(stage: Stage) {
        Config.init()

        val scene = Scene(Pane(), 1024.0, 576.0)

        stage.title = "LibrePass"
        stage.scene = scene
        stage.icons.add(Image(App::class.java.getResourceAsStream("/img/logo.png")))
        App.hostServices = hostServices!!
        App.stage = stage

        val resources: ResourceBundle = ResourceBundle.getBundle("/locales/locale", Locale.getDefault())

        StateManager.init(scene, resources)
        StyleManager.init()

        if (Config.isObjectExists("credentials") && Config.isObjectExists("user_secrets")) {
            val state = StateManager.getState(State.DASHBOARD)
            val controller = state.getController<DashboardController>()
            controller.credentials = Config.readObject("credentials")
            controller.userSecrets = Config.readObject("user_secrets")
            StateManager.applyState(state)
        } else {
            StateManager.setState(State.WELCOME)
        }

        StyleManager.trackScene(scene)
        StyleManager.trackStage(stage)
        StyleManager.setStyle(Config.readObject<Settings>("settings").theme)
        StyleManager.reloadStyle()
        stage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}
