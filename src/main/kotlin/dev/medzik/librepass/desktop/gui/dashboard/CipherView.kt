package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.layout.AnchorPane

private const val BULLET = '\u2022'
class CipherView : AnchorPane() {

    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var password: Label

    @FXML
    private lateinit var username: Label

    private lateinit var cipher: Cipher

    @FXML
    private lateinit var passwordToggleShow: ToggleButton

    init {
        Fxml.loadComponent("/fxml/dashboard/cipherview.fxml", this)

        passwordToggleShow.selectedProperty().addListener { _, _, selected ->
            setPassword(cipher.loginData?.password!!, !selected)
        }
    }

    fun setCipher(cipher: Cipher) {
        this.cipher = cipher

        name.text = cipher.loginData?.name
        username.text = cipher.loginData?.username

        setPassword(cipher.loginData?.password!!, true)
        passwordToggleShow.isSelected = false
    }

    private fun setPassword(text: String, mask: Boolean) {
        password.text = ""
        if (mask) {
            for (i in text)
                password.text += BULLET
        } else password.text = text
    }
}
