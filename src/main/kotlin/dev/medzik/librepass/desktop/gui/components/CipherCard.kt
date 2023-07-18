package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import java.io.IOException

class CipherCard(
    cipher: Cipher
) : AnchorPane() {

    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var username: Label

    init {
        try {
            val loader = FXMLLoader(CipherCard::class.java.getResource("/fxml/components/ciphercard.fxml"))
            loader.setController(this)
            loader.setRoot(this)
            loader.load<AnchorPane>()

            name.text = cipher.loginData?.name
            username.text = cipher.loginData?.username
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
