package dev.medzik.librepass.desktop.gui.auth

import dev.medzik.librepass.client.api.AuthClient
import dev.medzik.librepass.client.errors.ApiException
import dev.medzik.librepass.client.utils.Cryptography.computePasswordHash
import dev.medzik.librepass.desktop.gui.Controller
import dev.medzik.librepass.desktop.gui.dashboard.DashboardController
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.utils.Utils
import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import java.util.concurrent.CompletableFuture

class LoginController : Controller() {

    @FXML
    private lateinit var email: TextField

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var login: Button

    private val authClient = AuthClient()

    @FXML
    private fun initialize() {
        val listener = ChangeListener<String> { _, _, _ ->
            login.isDisable = !email.text.contains("@") || password.text.isEmpty()
        }
        email.textProperty().addListener(listener)
        password.textProperty().addListener(listener)
        login.isDisable = true
    }

    @FXML
    fun onBack() {
        StateManager.setState(State.WELCOME)
    }

    @FXML
    fun onLogin() {
        submit(email.text, password.text)
    }

    private fun submit(email: String, password: String) = CompletableFuture.runAsync {
        login.isDisable = true
        try {
            val preLogin = authClient.preLogin(email)

            // compute base password hash
            val passwordHash = computePasswordHash(
                password = password,
                email = email,
                argon2Function = preLogin.toArgon2()
            )

            // authenticate user and get credentials
            val credentials = authClient.login(
                email = email,
                passwordHash = passwordHash
            )

            // Perform GC to flush a lot of memory allocated by argon2 generation
            System.gc()

            val state = StateManager.getState(State.DASHBOARD)
            val controller = state.getController<DashboardController>()
            controller.credentials = credentials
            StateManager.applyState(state)
        } catch (e: ApiException) {
            Utils.dialog("Error!", e.message, Alert.AlertType.ERROR)
        }

        login.isDisable = false
    }

    override fun onStop() {
        email.clear()
        password.clear()
    }
}
