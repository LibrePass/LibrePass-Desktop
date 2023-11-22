package dev.medzik.librepass.desktop.gui.components.cipher

import dev.medzik.librepass.desktop.gui.dashboard.CipherView
import javafx.scene.control.ToggleButton
import javafx.scene.image.Image
import javafx.scene.image.ImageView

private const val BULLET = '\u2022'
class PasswordCipherEntry(name: String) : CopyCipherEntry(name) {
    private val passwordToggleButton: ToggleButton = ToggleButton()

    private val visibleImage = Image(CipherView::class.java.getResourceAsStream("/img/dashboard/visibility.png"))
    private val visibleOffImage = Image(CipherView::class.java.getResourceAsStream("/img/dashboard/visibility_off.png"))

    private lateinit var password: String

    init {
        passwordToggleButton.apply {
            setPrefSize(45.0,45.0)
            setMinSize(45.0,45.0)
            setMaxSize(45.0,45.0)
        }
        passwordToggleButton.graphic = ImageView(visibleOffImage).apply {
            isPreserveRatio = true
            fitWidth = 48.0
            fitHeight = 28.0
        }

        buttonsPane.children.clear()
        buttonsPane.children.add(passwordToggleButton)
        buttonsPane.children.add(copyButton)

        passwordToggleButton.selectedProperty().addListener { _, _, selected ->
            (passwordToggleButton.graphic as ImageView).image = if (selected) visibleImage else visibleOffImage
            setPassword(password, !selected)
        }
        copyButton.setOnAction {
            copy(password)
        }
    }

    fun setValue(text: String,mask: Boolean) {
        this.password = text
        setPassword(password, mask)
    }

    private fun setPassword(
        text: String?,
        mask: Boolean
    ) {
        value.text = ""
        if (!text.isNullOrEmpty()) {
            if (mask) {
                for (i in text)
                    value.text += BULLET
            } else {
                value.text = text
            }
        }
    }

}