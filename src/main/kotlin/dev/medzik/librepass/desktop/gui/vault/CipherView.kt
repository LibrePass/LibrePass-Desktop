package dev.medzik.librepass.desktop.gui.vault

import dev.medzik.librepass.desktop.gui.components.cipher.section.*
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import dev.medzik.librepass.types.cipher.CipherType
import javafx.fxml.FXML
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox

class CipherView : AnchorPane() {
    @FXML
    private lateinit var sections: VBox

    private val loginDataSection = LoginDataSection()
    private val websiteSection = WebsiteSection()
    private val otherSection = OtherSection()

    private val secureNoteSection = SecureNoteSection()

    private val creditCardSection = CreditCardSection()

    init {
        Fxml.loadComponent("/fxml/vault/cipher-view.fxml", this)
    }

    fun setCipher(cipher: Cipher) {
        sections.children.clear()

        when (cipher.type) {
            CipherType.Login -> {
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
            CipherType.SecureNote -> {
                sections.children.add(secureNoteSection)
                secureNoteSection.setCipher(cipher)
            }
            CipherType.Card -> {
                sections.children.add(creditCardSection)
                creditCardSection.setCipher(cipher)
            }
        }
    }
}
