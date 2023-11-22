package dev.medzik.librepass.desktop.gui.components.cipher

import dev.medzik.librepass.desktop.utils.Fxml
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.HBox


open class CipherEntry(
    val name: String,
    viewToLoad: String = "/fxml/dashboard/cipher/cipherentry.fxml"
) : AnchorPane() {

    @FXML
    private lateinit var nameLabel: Label

    @FXML
    protected lateinit var buttonsPane: HBox

    @FXML
    protected lateinit var value: Label;
    init {
        Fxml.loadComponent(viewToLoad,this,this)
    }

    @FXML
    fun initialize() {
        nameLabel.text = name
    }

    open fun setValue(text: String) {
        value.text = text
    }
}