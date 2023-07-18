package dev.medzik.librepass.desktop.gui.dashboard

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.client.api.UserCredentials
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.components.CipherCard
import dev.medzik.librepass.types.cipher.Cipher
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox

open class DashboardController : Controller() {

    lateinit var credentials: UserCredentials

    private lateinit var cipherClient: CipherClient

    @FXML
    private lateinit var cipherList: ListView<AnchorPane>

    @FXML
    private lateinit var mainPanel: VBox

    @FXML
    private fun initialize() {
//        cipherList.setCellFactory {
//            object : ListCell<CipherCard?>() {
//                override fun updateItem(item: CipherCard?, empty: Boolean) {
//                    super.updateItem(item, empty)
//                    graphic = item;
//                }
//            }
//        }
    }

    override fun onStart() {
        cipherClient = CipherClient(credentials.apiKey)

        val ciphersResponse = cipherClient.getAll()

        for (cipher in ciphersResponse) {
            println("Executed")
            val card = CipherCard(Cipher(cipher, credentials.secretKey))
            mainPanel.children.add(card)
            // cipherList.items.add(card)
        }

        super.onStart()
    }
}
