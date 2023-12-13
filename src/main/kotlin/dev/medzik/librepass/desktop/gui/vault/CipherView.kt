package dev.medzik.librepass.desktop.gui.vault

import dev.medzik.librepass.desktop.gui.components.cipher.section.LoginDataSection
import dev.medzik.librepass.desktop.gui.components.cipher.section.OtherSection
import dev.medzik.librepass.desktop.gui.components.cipher.section.WebsiteSection
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox

class CipherView : AnchorPane() {

    @FXML
    private lateinit var sections: VBox

    private val loginDataSection = LoginDataSection()
    private val websiteSection = WebsiteSection()
    private val otherSection = OtherSection()

    init {
        Fxml.loadComponent("/fxml/vault/cipher-view.fxml", this)
    }

    fun setCipher(cipher: Cipher) {
        sections.children.clear()

        sections.children.add(loginDataSection)
        loginDataSection.setCipher(cipher)

        if (!cipher.loginData?.uris.isNullOrEmpty()) {
            sections.children.add(websiteSection)
            websiteSection.setCipher(cipher)
        }


        if (!cipher.loginData?.notes.isNullOrEmpty()) {
            sections.children.add(otherSection)
            otherSection.setCipher(cipher)
        }
    }
}
