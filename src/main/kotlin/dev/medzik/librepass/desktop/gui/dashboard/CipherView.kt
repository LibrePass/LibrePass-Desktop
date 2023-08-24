package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.AnchorPane

private const val BULLET = '\u2022'

// TODO: Split into components
class CipherView : AnchorPane() {

    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var passwordToggleIcon: ImageView

    @FXML
    private lateinit var password: Label

    @FXML
    private lateinit var username: Label

    @FXML
    private lateinit var notes: Label

    private lateinit var cipher: Cipher

    @FXML
    private lateinit var passwordToggleShow: ToggleButton

    private val visibleImage = Image(CipherView::class.java.getResourceAsStream("/img/dashboard/visibility.png"))
    private val visibleOffImage = Image(CipherView::class.java.getResourceAsStream("/img/dashboard/visibility_off.png"))

    init {
        Fxml.loadComponent("/fxml/dashboard/cipherview.fxml", this)

        passwordToggleShow.selectedProperty().addListener { _, _, selected ->
            passwordToggleIcon.image = if (selected) visibleImage else visibleOffImage
            setPassword(cipher.loginData?.password!!, !selected)
        }
    }

    fun setCipher(cipher: Cipher) {
        this.cipher = cipher

        name.text = cipher.loginData?.name
        username.text = cipher.loginData?.username
        notes.text = cipher.loginData?.notes

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

    @FXML
    fun onUsernameCopy() = copy(username.text)

    @FXML
    fun onPasswordCopy() = copy(cipher.loginData?.password!!)

    @FXML
    fun onNotesCopy() = copy(notes.text)

    private fun copy(text: String) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text)
        clipboard.setContent(content)
    }
}
