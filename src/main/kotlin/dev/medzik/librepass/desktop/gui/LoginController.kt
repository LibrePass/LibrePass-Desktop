package dev.medzik.librepass.desktop.gui

import dev.medzik.librepass.client.api.v1.AuthClient
import dev.medzik.librepass.client.utils.Cryptography.computePasswordHash
import dev.medzik.librepass.desktop.state.State
import dev.medzik.librepass.desktop.state.StateManager
import dev.medzik.librepass.desktop.utils.Utils

import javafx.beans.value.ChangeListener
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import java.io.IOException
import java.util.concurrent.CompletableFuture

class LoginController {

    @FXML
    private lateinit var email: TextField

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var login: Button

    private val authClient = AuthClient()

    @FXML
    fun initialize() {
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
        submit(email.text,password.text)
    }

    private fun submit(email: String, password: String) {
        CompletableFuture.runAsync {

            login.isDisable = true
            try {
                // get argon2id parameters
                val argon2idParameters = authClient.getUserArgon2idParameters(email)

                // compute base password hash
                val passwordHash = computePasswordHash(
                    password = password,
                    email = email,
                    parameters = argon2idParameters
                )

                // authenticate user and get credentials
                val credentials = authClient.login(
                    email = email,
                    passwordHash = passwordHash
                )

                Utils.dialog("Logged in!",credentials.userId.toString(),Alert.AlertType.INFORMATION)
            } catch (e: IOException) {
                Utils.dialog("Error!",e.message,Alert.AlertType.ERROR)
            }

            login.isDisable = false
        }


//        try {
//
//
////            // insert credentials into local database
////            credentialsDao.insert(
////                Credentials(
////                    userId = credentials.userId,
////                    email = email,
////                    apiKey = credentials.apiKey,
////                    publicKey = credentials.publicKey,
////                    // Argon2id parameters
////                    memory = argon2idParameters.memory,
////                    iterations = argon2idParameters.iterations,
////                    parallelism = argon2idParameters.parallelism,
////                    version = argon2idParameters.version
////                )
////            )
//
////            // navigate to dashboard
////            scope.launch(Dispatchers.Main) {
////                navController.navigate(
////                    screen = Screen.Dashboard,
////                    arguments = listOf(
////                        Argument.SecretKey to credentials.secretKey,
////                        Argument.PrivateKey to credentials.privateKey
////                    ),
////                    disableBack = true
////                )
////            }
//        } catch (e: Exception) {
////            loading = false
////
////            e.handle(context, snackbarHostState)
//        }
    }
}