package dev.medzik.librepass.desktop.style

enum class Style(
    val hook: () -> Unit,
    vararg val styles: String
) {
    LIGHT({}, "/style/default/shared.css", "/style/default/light.css"),
    DARK({}, "/style/default/shared.css", "/style/default/dark.css");

    fun invokeHook() = hook.invoke()
}
