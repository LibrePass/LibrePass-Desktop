package dev.medzik.librepass.desktop.style

enum class Style(vararg val styles: String) {
    LIGHT("/style/shared/shared.css", "/style/light/light.css"),
    DARK("/style/shared/shared.css", "/style/dark/dark.css")
}
