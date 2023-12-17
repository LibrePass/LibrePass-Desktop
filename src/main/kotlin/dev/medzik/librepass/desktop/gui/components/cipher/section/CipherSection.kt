package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CipherEntry
import dev.medzik.librepass.desktop.utils.Fxml
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox

open class CipherSection(
    val name: String = ""
) : VBox() {
    @FXML
    protected lateinit var nameLabel: Label

    @FXML
    private lateinit var entriesPane: VBox

    @FXML
    protected lateinit var beforePane: VBox

    init {
        Fxml.loadComponent("/fxml/components/cipher/cipher-section.fxml", this, this)
    }

    @FXML
    fun initialize() {
        nameLabel.text = name
    }

    fun clearAllEntries() {
        entriesPane.children.clear()
    }

    fun addEntry(entry: CipherEntry) {
        entriesPane.children.add(entry)
    }
}
