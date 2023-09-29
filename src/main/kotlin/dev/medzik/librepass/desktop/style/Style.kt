package dev.medzik.librepass.desktop.style

enum class Style(
    val hook: () -> Unit,
    vararg val styles: String
) {
    LIGHT({}, "/style/shared.css", "/style/light.css"),
    DARK({}, "/style/shared.css", "/style/dark.css");

    fun invokeHook() = hook.invoke()
}
