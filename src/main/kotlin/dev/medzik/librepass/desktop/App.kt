package dev.medzik.librepass.desktop

import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Settings
import dev.medzik.librepass.desktop.gui.dashboard.DashboardController
import dev.medzik.librepass.desktop.locale.LangManager
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.application.Application
import javafx.application.HostServices
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Stage

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

        val resources = LangManager.init()

        StateManager.init(scene, resources)
        StyleManager.init()
        StyleManager.trackScene(scene)
        StyleManager.setStyle(Config.readObject<Settings>("settings").theme)
        StyleManager.reloadStyle()

        if (Config.isObjectExists("credentials") && Config.isObjectExists("user_secrets")) {
            val state = StateManager.getState(State.DASHBOARD)
            val controller = state.getController<DashboardController>()
            controller.credentials = Config.readObject("credentials")
            controller.userSecrets = Config.readObject("user_secrets")
            StateManager.applyState(state)
        } else {
            StateManager.setState(State.WELCOME)
        }

        stage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}
