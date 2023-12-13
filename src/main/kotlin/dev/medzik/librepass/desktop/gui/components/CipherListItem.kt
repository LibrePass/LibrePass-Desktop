package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.desktop.config.Cache
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
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

            name.text = cipher!!.loginData?.name
            username.text = cipher.loginData?.username

            updateIcon(cipher)
            graphic = root
        }
    }

    private fun updateIcon(cipher: Cipher) {
        val urls = cipher.loginData?.uris

        if (!urls.isNullOrEmpty()) {
            Cache.cacheIcon(urls[0], icon)
        } else {
            icon.image = userIcon
        }
    }
}
