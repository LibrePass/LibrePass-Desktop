package dev.medzik.librepass.desktop.style

enum class Style(
    val hook: () -> Unit,
    vararg val styles: String
) {
    LIGHT({}, "/style/shared/shared.css", "/style/light/light.css"),
    DARK({}, "/style/shared/shared.css", "/style/dark/dark.css");

    fun invokeHook() = hook.invoke()
}
