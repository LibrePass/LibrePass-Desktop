package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.desktop.config.Credentials
import dev.medzik.librepass.desktop.config.DataStoreUserSecrets
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.components.CipherListItem
import dev.medzik.librepass.types.cipher.Cipher
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import java.util.concurrent.CompletableFuture

class DashboardController : Controller() {

    lateinit var credentials: Credentials
    lateinit var userSecrets: DataStoreUserSecrets

    private lateinit var cipherClient: CipherClient

    @FXML
    lateinit var cipherList: ListView<CipherListItem>

    private val list = FXCollections.observableArrayList<CipherListItem>()

    @FXML
    private fun initialize() {
        cipherList.items = list
    }

    override fun onStart() {
        super.onStart()

        CompletableFuture.runAsync {
            cipherClient = CipherClient(credentials.apiKey)

            val ciphersResponse = cipherClient.getAll()

            for (cipher in ciphersResponse) {
                val cipherElement = Cipher(cipher, userSecrets.secretKey)

                val item = CipherListItem(cipherElement)
                Platform.runLater { list.add(item) }
            }
        }
    }

    override fun onStop() =
        list.clear()
}
