package dev.medzik.librepass.desktop.gui.components.cipher

import dev.medzik.librepass.desktop.utils.Fxml
import javafx.scene.layout.VBox

class CipherSection(
    val name: String
) : VBox() {
    init {
        Fxml.loadComponent("/fxml/dashboard/cipher/ciphersection.fxml", this, this)
    }
}
