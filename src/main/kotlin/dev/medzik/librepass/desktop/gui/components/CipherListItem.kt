package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.desktop.config.Cache
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import dev.medzik.librepass.types.cipher.CipherType
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane

class CipherListItem : ListCell<Cipher>() {
    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var username: Label

    @FXML
    private lateinit var icon: ImageView

    private var root: AnchorPane = AnchorPane()

    private var loaded = false

    private var currentCipher: Cipher? = null

    companion object {
        private val userIcon = Image(CipherListItem::class.java.getResourceAsStream("/img/dashboard/user.png"))
        private val notesIcon = Image(CipherListItem::class.java.getResourceAsStream("/img/dashboard/notes.png"))
        private val creditCardIcon = Image(CipherListItem::class.java.getResourceAsStream("/img/dashboard/credit_card.png"))
    }

    init {
        if (!loaded) {
            val loader = Fxml.getLoader("/fxml/components/cipher-list-item.fxml")
            loader.setRoot(root)
            loader.setController(this)
            loader.load<AnchorPane>()

            loaded = true
        }
    }

    override fun updateItem(
        cipher: Cipher?,
        empty: Boolean
    ) {
        super.updateItem(cipher, empty)
        if (empty) {
            graphic = null
            icon.image = null
        } else {
            currentCipher = cipher

            name.text = ""
            username.text = ""

            when (cipher!!.type) {
                CipherType.Login -> {
                    name.text = cipher.loginData?.name
                    username.text = cipher.loginData?.username
                }
                CipherType.SecureNote -> name.text = cipher.secureNoteData?.title
                CipherType.Card -> {
                    name.text = cipher.cardData?.name
                    username.text = "•••• " + cipher.cardData!!.number.takeLast(4)
                }
            }

            updateIcon(cipher)
            graphic = root
        }
    }

    private fun updateIcon(cipher: Cipher) {
        when (cipher.type) {
            CipherType.Login -> {
                val urls = cipher.loginData?.uris

                if (!urls.isNullOrEmpty()) {
                    try {
                        Cache.cacheIcon(urls[0], icon)
                    } catch (_: Exception) {
                    }
                } else {
                    icon.image = userIcon
                }
            }
            CipherType.SecureNote -> {
                icon.image = notesIcon
            }
            CipherType.Card -> {
                icon.image = creditCardIcon
            }
        }
    }
}
