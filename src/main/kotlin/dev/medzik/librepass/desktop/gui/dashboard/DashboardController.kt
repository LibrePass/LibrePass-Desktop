package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.client.api.UserCredentials
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.components.CipherListItem
import dev.medzik.librepass.types.cipher.Cipher
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*

class DashboardController : Controller() {

    lateinit var credentials: UserCredentials

    private lateinit var cipherClient: CipherClient

    @FXML
    lateinit var cipherList: ListView<CipherListItem>

    private val list = FXCollections.observableArrayList<CipherListItem>()

    @FXML
    private fun initialize() {
        cipherList.items = list
    }

    override fun onStart() {
        cipherClient = CipherClient(credentials.apiKey)

        val ciphersResponse = cipherClient.getAll()

        for (cipher in ciphersResponse) {
            val cipherElement = Cipher(cipher, credentials.secretKey)

            val item = CipherListItem(cipherElement)
            list.add(item)
        }

        super.onStart()
    }

    override fun onStop() =
        list.clear()
}
