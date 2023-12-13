package dev.medzik.librepass.desktop.gui.vault

import dev.medzik.librepass.client.api.CipherClient
import dev.medzik.librepass.client.errors.ClientException
import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Credentials
import dev.medzik.librepass.desktop.config.StoreCipher
import dev.medzik.librepass.desktop.config.UserSecrets
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.components.CipherListItem
import dev.medzik.librepass.desktop.locale.LangManager
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.desktop.utils.Fxml
import dev.medzik.librepass.types.cipher.Cipher
import dev.medzik.librepass.types.cipher.CipherType
import dev.medzik.librepass.types.cipher.data.CipherLoginData
import dev.medzik.librepass.utils.fromHexString
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import java.util.*
import java.util.concurrent.CompletableFuture

class VaultController : Controller() {
    lateinit var credentials: Credentials
    lateinit var userSecrets: UserSecrets

    @FXML
    lateinit var menuController: MenuController

    @FXML
    lateinit var cipherList: ListView<Cipher>

    @FXML
    lateinit var statusLabel: Label

    @FXML
    lateinit var search: TextField

    @FXML
    lateinit var rootBorderPane: BorderPane

    private val list = FXCollections.observableArrayList<Cipher>()

    private val searchList = FXCollections.observableArrayList<Cipher>()

    private val cipherView = CipherView()
    private val emptyView = loadEmptyView()

    @FXML
    private fun initialize() {
        menuController.dashboard = this

        cipherList.items = list
        cipherList.setCellFactory { CipherListItem() }
        cipherList.selectionModel.selectedItemProperty().addListener { _, _, cipher ->
            rootBorderPane.center =
                if (cipher != null) {
                    cipherView.setCipher(cipher)
                    cipherView
                } else {
                    emptyView
                }
        }

        search.textProperty().addListener { _, _, searchPhrase -> searchFieldEdited(searchPhrase) }

        rootBorderPane.center = emptyView
    }

    private fun searchFieldEdited(searchText: String) {
        if (searchText.isNotBlank()) {
            searchList.clear()

            val filteredCiphers =
                list.filter {
                    it.loginData!!.name.lowercase().contains(searchText) || it.loginData!!.username?.lowercase()
                        ?.contains(searchText) ?: false
                }

            for (cipher in filteredCiphers)
                searchList.add(cipher)

            cipherList.items = searchList
        } else {
            cipherList.items = list
            searchList.clear()
        }
    }

    private fun loadEmptyView(): VBox {
        val loader = Fxml.getLoader("/fxml/vault/empty-view.fxml")
        loader.resources = LangManager.getLocale()
        return loader.load()
    }

    // get ciphers from local repository and update UI
    // TODO: Add more database like storage
    private fun updateLocalCiphers() {
        val ciphers =
            if (Config.isObjectExists("ciphers")) {
                Config.readObject<List<StoreCipher>>("ciphers")
            } else {
                val list = mutableListOf<StoreCipher>()
                Config.writeObject("ciphers", list)
                list
            }

        val decryptedCiphers =
            ciphers.map {
                try {
                    Cipher(it.encryptedCipher, userSecrets.secretKey.fromHexString())
                } catch (e: Exception) {
                    // TODO
                    Cipher(
                        id = it.encryptedCipher.id,
                        owner = it.owner,
                        type = CipherType.Login,
                        loginData =
                        CipherLoginData(
                            name = "Encryption error"
                        )
                    )
                }
            }
        Platform.runLater {
            cipherList.items = null

            list.clear()
            // sort ciphers by name and update UI
            decryptedCiphers.sortedBy { it.loginData!!.name }.forEach { list.add(it) }

            cipherList.items = list
        }
    }

    fun updateCiphers() {
        try {
            setStatus(tr("status.sync"))

            val cachedCiphers = Config.readObject<MutableList<StoreCipher>>("ciphers")
            cachedCiphers.map { if (it.owner != credentials.userId) cachedCiphers.remove(it) }

            val lastSync = credentials.lastSync

            if (lastSync != null) {
                // get ciphers from API
                val syncResponse = CipherClient(credentials.apiKey).sync(Date(lastSync * 1000))

                // update last sync date
                credentials = Config.overrideObject("credentials") { credentials.copy(lastSync = Date().time / 1000) }

                // delete ciphers from the local database that are not in API response
                for (i in 0..<cachedCiphers.size) {
                    if (cachedCiphers[i].encryptedCipher.id !in syncResponse.ids) {
                        cachedCiphers.remove(cachedCiphers[i])
                    }
                }

                // add ciphers from response
                for (cipher in syncResponse.ciphers) {
                    cachedCiphers.add(
                        StoreCipher(
                            id = cipher.id,
                            owner = cipher.owner,
                            encryptedCipher = cipher
                        )
                    )
                }

                Config.writeObject("ciphers", cachedCiphers)
            } else {
                // update last sync date
                credentials = Config.overrideObject("credentials") { credentials.copy(lastSync = Date().time / 1000) }

                // get all ciphers from API
                val ciphersResponse = CipherClient(credentials.apiKey).getAll()

                val ciphers = mutableListOf<StoreCipher>()
                for (cipher in ciphersResponse) {
                    ciphers.add(
                        StoreCipher(
                            id = cipher.id,
                            owner = cipher.owner,
                            encryptedCipher = cipher
                        )
                    )
                }
                Config.writeObject("ciphers", ciphers)
            }
            resetStatus()
        } catch (e: Exception) {
            if (e is ClientException)
                setStatus(tr("status.error.network"))
            else {
                setStatus("Error: ${e.message}")
                throw RuntimeException(e)
            }
        } finally {
            // get cipher from local repository and update UI
            updateLocalCiphers()
        }
    }

    @FXML
    fun onCreateNewCipher() {
        val stage = CipherAddWindow()
        stage.show()
    }

    override fun onStart() {
        super.onStart()

        CompletableFuture.runAsync {
            updateLocalCiphers()
            updateCiphers()
        }
    }

    override fun onStop() = list.clear()

    private fun setStatus(name: String) = Platform.runLater { statusLabel.text = name }

    private fun resetStatus() = Platform.runLater { statusLabel.text = "" }
}
