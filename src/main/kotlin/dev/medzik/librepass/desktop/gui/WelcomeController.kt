package dev.medzik.librepass.desktop.gui

import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.Style
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.animation.FadeTransition
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.ImageView
import javafx.util.Duration
import org.controlsfx.control.ToggleSwitch
import java.net.URL
import java.util.*

class WelcomeController : Controller(), Initializable {
    @FXML
    private lateinit var logo: ImageView

    private lateinit var fadeInTransition: FadeTransition

    @FXML
    private lateinit var themeSwitch: ToggleSwitch

    @FXML
    override fun initialize(location: URL, resources: ResourceBundle) {
        fadeInTransition = FadeTransition(Duration.seconds(1.6), logo).apply {
            fromValue = 0.0
            toValue = 1.0
        }

        themeSwitch.selectedProperty().addListener { _, _, _ ->
            StyleManager.setStyle(if (themeSwitch.isSelected) Style.DARK else Style.LIGHT)
            themeSwitch.text = if (themeSwitch.isSelected) resources.getString("dark") else resources.getString("light")
            StyleManager.reloadStyle()
        }
    }

    override fun onStart() {
        fadeInTransition.play()
    }

    @FXML
    fun onLogin() {
        StateManager.setState(State.LOGIN)
    }

    @FXML
    fun onRegister() {
        StateManager.setState(State.REGISTER)
    }
}
