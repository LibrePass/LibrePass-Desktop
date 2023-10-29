package dev.medzik.librepass.desktop.gui

import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Settings
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.Style
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.animation.FadeTransition
import javafx.fxml.FXML
import javafx.scene.image.ImageView
import javafx.util.Duration
import org.controlsfx.control.ToggleSwitch

class WelcomeController : Controller() {
    @FXML
    private lateinit var logo: ImageView

    private lateinit var fadeInTransition: FadeTransition

    @FXML
    private lateinit var themeSwitch: ToggleSwitch

    @FXML
    private fun initialize() {
        fadeInTransition =
            FadeTransition(Duration.seconds(1.6), logo).apply {
                fromValue = 0.0
                toValue = 1.0
            }

        // reading theme
        val theme = Config.readObject<Settings>("settings").theme
        themeSwitch.isSelected = theme == Style.DARK
        themeSwitch.text = if (theme == Style.DARK) tr("dark") else tr("light")

        themeSwitch.selectedProperty().addListener { _, _, _ ->
            val style = if (themeSwitch.isSelected) Style.DARK else Style.LIGHT

            Config.overrideObject<Settings>("settings") { theme -> theme.copy(theme = style) }

            StyleManager.setStyle(style)
            themeSwitch.text = if (themeSwitch.isSelected) tr("dark") else tr("light")
            StyleManager.reloadStyle()
        }
    }

    override fun onStart() = fadeInTransition.play()

    @FXML
    fun onLogin() = StateManager.setState(State.LOGIN)

    @FXML
    fun onRegister() = StateManager.setState(State.REGISTER)
}
