package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.types.cipher.Cipher
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import java.net.URL
import java.util.concurrent.CompletableFuture

class CipherListItem(
    cipher: Cipher
) : AnchorPane() {
    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var username: Label

    @FXML
    private lateinit var icon: ImageView

    companion object {
        @JvmStatic
        private var loader: FXMLLoader = FXMLLoader(CipherListItem::class.java.getResource("/fxml/components/cipherlistitem.fxml"))
    }

    init {
        loader.setController(this)
        loader.setRoot(this)
        loader.load<AnchorPane>()

        name.text = cipher.loginData?.name
        username.text = cipher.loginData?.username

        CompletableFuture.runAsync {
            val url = CipherClient.getFavicon(cipher.loginData?.uris?.get(0)!!)
            val image = URL("https://librepass-api.medzik.dev$url").openStream()
            Platform.runLater { icon.image = Image(image) }
        }
    }
}
