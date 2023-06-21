package dev.medzik.librepass.desktop.gui

import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import javafx.animation.FadeTransition
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.util.Duration

class WelcomeController : Controller {
    @FXML
    private lateinit var logo: ImageView

    @FXML
    private lateinit var register: Button

    private lateinit var fadeInTransition: FadeTransition

    @FXML
    private fun initialize() {
        fadeInTransition = FadeTransition(Duration.seconds(1.6), logo).apply {
            fromValue = 0.0
            toValue = 1.0
        }
    }

    override fun onStart() {
        fadeInTransition.play()
    }

    @FXML
    fun onLogin() {
        StateManager.setState(State.LOGIN)
    }
}
