package dev.medzik.librepass.desktop.utils

import dev.medzik.librepass.desktop.gui.components.CipherListItem
import dev.medzik.librepass.desktop.locale.LangManager
import javafx.fxml.FXMLLoader

object Fxml {
    fun getLoader(path: String) = FXMLLoader(CipherListItem::class.java.getResource(path))

    fun load(
        path: String,
        controller: Any?,
        rootNode: Any? = controller
    ) {
        val loader = FXMLLoader(CipherListItem::class.java.getResource(path))
        loader.resources = LangManager.getLocale()
        loader.setController(controller)
        loader.setRoot(rootNode)
        loader.load<Any>()
    }

    fun loadComponent(
        path: String,
        controller: Any?,
        rootNode: Any? = controller
    ) {
        val loader = FXMLLoader(CipherListItem::class.java.getResource(path))
        loader.resources = LangManager.getLocale()
        loader.setControllerFactory { controller }
        loader.setRoot(rootNode)
        loader.load<Any>()
    }
}
