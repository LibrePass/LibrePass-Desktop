package dev.medzik.librepass.desktop.gui.auth

import dev.medzik.librepass.client.api.AuthClient
import dev.medzik.librepass.client.errors.ApiException
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.utils.Utils
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterController {
    @FXML
    private lateinit var email: TextField

    @FXML
    private lateinit var hint: TextField

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var register: Button

    @FXML
    private lateinit var retypePassword: PasswordField

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val authClient = AuthClient()

    @FXML
    private fun initialize() {
        val listener =
            ChangeListener<String> { _, _, _ ->
                register.isDisable = !email.text.contains("@") || password.text.isEmpty() || password.text != retypePassword.text
            }
        email.textProperty().addListener(listener)
        password.textProperty().addListener(listener)
        retypePassword.textProperty().addListener(listener)
        register.isDisable = true
    }

    @FXML
    fun onBack() {
        StateManager.setState(State.WELCOME)

        email.clear()
        password.clear()
        retypePassword.clear()
        hint.clear()
    }

    private fun submit(
        email: String,
        password: String,
        passwordHint: String
    ) = coroutineScope.launch {
        register.isDisable = true
        try {
            authClient.register(email, password, passwordHint)
            Utils.dialog("Registered!", "Registered!", Alert.AlertType.INFORMATION)
        } catch (e: ApiException) {
            Utils.dialog("Error!", e.message, Alert.AlertType.ERROR)
        }
        register.isDisable = false
    }

    @FXML
    fun onRegister() {
        submit(email.text, password.text, hint.text)
    }
}
