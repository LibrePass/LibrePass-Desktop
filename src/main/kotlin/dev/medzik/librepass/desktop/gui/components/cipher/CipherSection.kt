package dev.medzik.librepass.desktop.gui.components.cipher

import dev.medzik.librepass.desktop.utils.Fxml
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox

open class CipherSection(
    val name: String
) : VBox() {

    @FXML
    private lateinit var nameLabel: Label

    @FXML
    private lateinit var entriesPane: VBox

    init {
        Fxml.loadComponent("/fxml/dashboard/cipher/ciphersection.fxml", this, this)
    }

    @FXML
    fun initialize() {
        nameLabel.text = name;
    }

    fun clearAllEntries() {
        entriesPane.children.clear()
    }

    fun addEntry(entry: CipherEntry) {
        entriesPane.children.add(entry)
    }
}
