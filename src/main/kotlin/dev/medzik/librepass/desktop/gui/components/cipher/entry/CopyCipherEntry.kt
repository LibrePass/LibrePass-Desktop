package dev.medzik.librepass.desktop.gui.components.cipher.entry

import javafx.scene.control.Button
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent

open class CopyCipherEntry(name: String) : CipherEntry(name) {

    protected val copyButton: Button = Button().apply {
        setOnAction {
            copy(value.text)
        }
    }

    init {
        copyButton.apply {
            setPrefSize(45.0,45.0)
            setMinSize(45.0,45.0)
            setMaxSize(45.0,45.0)
        }
        copyButton.graphic = ImageView(Image(CopyCipherEntry::class.java.getResourceAsStream("/img/dashboard/copy.png"))).apply {
            isPreserveRatio = true
            fitWidth = 48.0
            fitHeight = 28.0
        }

        buttonsPane.children.add(copyButton)
    }

    protected fun copy(text: String) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text)
        clipboard.setContent(content)
    }
}