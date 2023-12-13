package dev.medzik.librepass.desktop.state

import javafx.fxml.FXMLLoader
import javafx.scene.layout.Pane
import java.util.*

enum class State(
    private val fxml: String
) {
    WELCOME("/fxml/welcome.fxml"),
    LOGIN("/fxml/auth/login.fxml"),
    REGISTER("/fxml/auth/register.fxml"),
    DASHBOARD("/fxml/vault/vault.fxml");

    private lateinit var fxmlLoader: FXMLLoader
    lateinit var rootPane: Pane

    fun load(resources: ResourceBundle) {
        fxmlLoader = FXMLLoader(StateManager::class.java.getResource(fxml))
        fxmlLoader.resources = resources
        rootPane = fxmlLoader.load()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getController(): T {
        return fxmlLoader.getController<Any>() as T
    }
}
