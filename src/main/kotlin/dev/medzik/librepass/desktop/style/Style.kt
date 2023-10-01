package dev.medzik.librepass.desktop.style

enum class Style(
    vararg val styles: String
) {
    LIGHT("/style/shared.css", "/style/light.css"),
    DARK("/style/shared.css", "/style/dark.css")
}
