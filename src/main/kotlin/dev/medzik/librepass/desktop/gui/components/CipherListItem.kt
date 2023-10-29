package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import java.net.URL

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

        // icon cache to prevent calling api
        private var iconsCache = HashMap<String, Image>()

        fun getIcon(url: String): Image? {
            return if (iconsCache.containsKey(url)) {
                iconsCache[url]
            } else {
                val icon = get(url)
                iconsCache[url] = icon
                icon
            }
        }

        private fun get(url: String): Image {
            val image = URL(url).openStream()
            return Image(image)
        }
    }

    override fun updateItem(
        cipher: Cipher?,
        empty: Boolean
    ) {
        super.updateItem(cipher, empty)
        if (empty) {
            graphic = null
            return
        } else if (currentCipher != cipher) {
            if (!loaded) {
                val loader = Fxml.getLoader("/fxml/components/cipherlistitem.fxml")
                loader.setRoot(root)
                loader.setController(this)
                loader.load<AnchorPane>()

                loaded = true
            }
        }

        currentCipher = cipher
        updateIcon(cipher!!)

        name.text = cipher.loginData?.name
        username.text = cipher.loginData?.username

        graphic = root
    }

    private fun updateIcon(cipher: Cipher) {
        val urls = cipher.loginData?.uris

        icon.image =
            if (!urls.isNullOrEmpty())
                getIcon(CipherClient.getFavicon(domain = urls[0]))
            else
                userIcon
    }
}
