package dev.medzik.librepass.desktop.gui.auth

import dev.medzik.librepass.client.api.AuthClient
import dev.medzik.librepass.client.errors.ApiException
import dev.medzik.librepass.desktop.config.Config
import dev.medzik.librepass.desktop.config.Credentials
import dev.medzik.librepass.desktop.config.UserSecrets
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
    fun onBack() =
        StateManager.setState(State.WELCOME)

    @FXML
    fun onLogin() {
        submit(email.text, password.text)
    }

    private fun submit(email: String, password: String) = CompletableFuture.runAsync {
        login.isDisable = true
        try {
            val preLogin = authClient.preLogin(email)

            // authenticate user and get credentials
            val loginCredentials = authClient.login(
                email = email,
                password = password
            )

            val credentials = Credentials(
                userId = loginCredentials.userId,
                email = email,
                apiKey = loginCredentials.apiKey,
                publicKey = loginCredentials.keyPair.publicKey,
                // Argon2id parameters
                memory = preLogin.memory,
                iterations = preLogin.iterations,
                parallelism = preLogin.parallelism
            )
            Config.writeObject("credentials", credentials)

            val userSecrets = UserSecrets(
                privateKey = loginCredentials.keyPair.privateKey,
                secretKey = loginCredentials.secretKey
            )
            Config.writeObject("user_secrets", userSecrets)

            // Perform GC to flush a lot of memory allocated by argon2 generation
            System.gc()

            val state = StateManager.getState(State.DASHBOARD)
            val controller = state.getController<DashboardController>()
            controller.credentials = credentials
            controller.userSecrets = userSecrets
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
