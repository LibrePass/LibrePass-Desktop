package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.desktop.App
import dev.medzik.librepass.desktop.locale.LangManager
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.desktop.style.StyleManager
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage

const val GITHUB_URL = "https://github.com/LibrePass"
const val WEBSITE_URL = "https://librepass.org"

class AboutDialog : Stage() {
    init {
        title = tr("about.title")

        val loader = FXMLLoader(AboutDialog::class.java.getResource("/fxml/components/about.fxml"))
        loader.setControllerFactory { this }
        loader.resources = LangManager.getLocale()
        val scene = Scene(loader.load())
        StyleManager.trackScene(scene)

        initModality(Modality.APPLICATION_MODAL)
        isResizable = false
        icons.add(Image(AboutDialog::class.java.getResourceAsStream("/img/logo.png")))
        setScene(scene)
    }

    @FXML
    fun onGithub() = App.hostServices.showDocument(GITHUB_URL)

    @FXML
    fun onWebsite() = App.hostServices.showDocument(WEBSITE_URL)
}
