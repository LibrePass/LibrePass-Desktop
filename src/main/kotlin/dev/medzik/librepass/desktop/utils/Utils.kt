package dev.medzik.librepass.desktop.utils

import dev.medzik.librepass.desktop.style.StyleManager
import javafx.application.Platform
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType

object Utils {
    fun dialog(title: String?, text: String?, type: AlertType?) {
        Platform.runLater {
            val alert = Alert(type)
            alert.title = title
            alert.headerText = null
            alert.contentText = text
            StyleManager.loadStyle(alert.dialogPane)
            alert.show()
        }
    }
}
