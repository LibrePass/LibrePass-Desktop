package dev.medzik.librepass.desktop.gui.components

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import java.net.URL
import java.util.concurrent.CompletableFuture

class CipherListItem(
    private val cipher: Cipher
) : AnchorPane() {
    @FXML
    private lateinit var name: Label

    @FXML
    private lateinit var username: Label

    @FXML
    private lateinit var icon: ImageView

    init {
        Fxml.load("/fxml/components/cipherlistitem.fxml", this)

        name.text = cipher.loginData?.name
        username.text = cipher.loginData?.username

        updateIcon()
    }

    private fun updateIcon() = CompletableFuture.runAsync {
        val urls = cipher.loginData?.uris!!

        if (urls.isNotEmpty()) {
            val url = CipherClient.getFavicon(cipher.loginData?.uris?.get(0)!!)
            val image = URL("https://api.librepass.medzik.dev$url").openStream()
            Platform.runLater { icon.image = Image(image) }
        }
    }
}
