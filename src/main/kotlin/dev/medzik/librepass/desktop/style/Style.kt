package dev.medzik.librepass.desktop.style

import javafx.scene.Scene
import javafx.scene.paint.Color

enum class Style(
    val onSet: (Scene) -> Unit,
    vararg val styles: String
) {
    LIGHT({ it.fill = Color.web("#f5f5f5") }, "/style/colors-light.css", "/style/controls.css", "/style/custom.css"),
    DARK({ it.fill = Color.web("#1e1e1e") }, "/style/controls.css", "/style/colors-dark.css", "/style/custom.css")
}
