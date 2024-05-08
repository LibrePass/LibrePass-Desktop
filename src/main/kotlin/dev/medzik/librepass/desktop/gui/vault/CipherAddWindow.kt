package dev.medzik.librepass.desktop.gui.vault

import dev.medzik.librepass.desktop.App
import dev.medzik.librepass.desktop.gui.components.AboutDialog
import dev.medzik.librepass.desktop.locale.LangManager
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.desktop.style.StyleManager
import dev.medzik.librepass.types.cipher.CipherType
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.StringConverter

class CipherAddWindow : Stage() {
    @FXML
    private lateinit var cipherType: ComboBox<CipherType>

    init {
        val loader = FXMLLoader(AboutDialog::class.java.getResource("/fxml/vault/cipher-add.fxml"))
        loader.setControllerFactory { this }
        loader.resources = LangManager.getLocale()

        val scene = Scene(loader.load(), 700.0, 400.0)
        StyleManager.trackScene(scene)

        initModality(Modality.APPLICATION_MODAL)

        this.scene = scene

        title = "Add new cipher"
        icons.add(Image(App::class.java.getResourceAsStream("/img/logo.png")))
        isResizable = false

        StyleManager.trackScene(scene)
        StyleManager.reloadStyle()
    }

    @FXML
    private fun initialize() {
        cipherType.converter =
            object : StringConverter<CipherType>() {
                override fun toString(obj: CipherType): String {
                    return when (obj) {
                        CipherType.Login -> tr("cipher.add.logindata")
                        CipherType.SecureNote -> tr("cipher.add.securenote")
                        CipherType.Card -> tr("cipher.add.creditcard")
                    }
                }

                override fun fromString(string: String?): CipherType? = null
            }
        cipherType.items.addAll(CipherType.Login, CipherType.SecureNote, CipherType.Card)
        cipherType.selectionModel.selectFirst()
    }

    @FXML
    fun onDone() {
        hide()
    }
}
