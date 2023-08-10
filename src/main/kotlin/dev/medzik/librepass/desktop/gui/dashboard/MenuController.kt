package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Settings
import dev.medzik.librepass.desktop.gui.components.AboutDialog
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.style.Style
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.MenuItem
import javafx.scene.control.RadioMenuItem
import javafx.scene.control.ToggleGroup

class MenuController {
    @FXML
    lateinit var debug: MenuItem

    @FXML
    lateinit var exit: MenuItem

    @FXML
    lateinit var theme: ToggleGroup

    @FXML
    lateinit var themeDark: RadioMenuItem

    @FXML
    lateinit var themeLight: RadioMenuItem

    private lateinit var aboutDialog: AboutDialog

    lateinit var dashboard: DashboardController

    @FXML
    private fun initialize() {
        aboutDialog = AboutDialog()

        // load theme settings
        val theme = Config.readObject<Settings>("settings").theme
        val radio = if (theme == Style.LIGHT) themeLight else themeDark
        radio.isSelected = true
    }

    @FXML
    fun onThemeRadio(event: ActionEvent) {
        val source = event.source

        val style: Style = if (source == themeDark) Style.DARK else Style.LIGHT

        Config.overrideObject<Settings>("settings") { theme -> theme.copy(theme = style) }

        StyleManager.setStyle(style)
        StyleManager.reloadStyle()
    }

    @FXML
    fun onLogout() {
        Config.deleteObject("credentials")
        Config.deleteObject("user_secrets")
        StateManager.applyState(State.WELCOME)
    }

    @FXML
    fun onExit() =
        Platform.exit()

    @FXML
    fun onAbout() = aboutDialog.show()

    @FXML
    fun onSync() = dashboard.updateCiphers()
}
