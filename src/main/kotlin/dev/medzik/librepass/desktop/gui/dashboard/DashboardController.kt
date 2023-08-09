package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.libcrypto.EncryptException
import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Credentials
import dev.medzik.librepass.desktop.config.DataStoreUserSecrets
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.components.CipherListItem
import dev.medzik.librepass.types.cipher.Cipher
import dev.medzik.librepass.types.cipher.CipherType
import dev.medzik.librepass.types.cipher.EncryptedCipher
import dev.medzik.librepass.types.cipher.data.CipherLoginData
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
    lateinit var cipherList: ListView<Cipher>

    private val list = FXCollections.observableArrayList<Cipher>()

    @FXML
    private fun initialize() {
        cipherList.items = list
        cipherList.setCellFactory { CipherListItem() }
    }

    // get ciphers from local repository and update UI
    fun updateLocalCiphers() {
        val ciphers = Config.readObject<List<EncryptedCipher>>("ciphers")

        val decryptedCiphers = ciphers.map {
            try {
                Cipher(it, userSecrets.secretKey)
            } catch (e: EncryptException) {
                Cipher(
                    id = it.id,
                    owner = it.owner,
                    type = CipherType.Login,
                    loginData = CipherLoginData(
                        name = "Encryption error"
                    )
                )
            }
        }

        // sort ciphers by name and update UI
        decryptedCiphers.sortedBy { it.loginData!!.name }.forEach { list.add(it) }
    }

    fun updateCiphers() {}

    override fun onStart() {
        super.onStart()

        CompletableFuture.runAsync {
            cipherClient = CipherClient(credentials.apiKey)

            val ciphersResponse = cipherClient.getAll()

            for (cipher in ciphersResponse) {
                val cipherElement = Cipher(cipher, userSecrets.secretKey)

                Platform.runLater { list.add(cipherElement) }
            }
        }
    }

    override fun onStop() =
        list.clear()
}
