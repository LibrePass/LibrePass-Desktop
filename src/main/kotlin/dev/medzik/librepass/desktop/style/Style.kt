package dev.medzik.librepass.desktop.style

import dev.medzik.librepass.desktop.utils.OS
import dev.medzik.librepass.desktop.utils.windows.FXWinUtil
import javafx.application.Platform
import javafx.stage.Stage

enum class Style(
    private val hook: (Stage) -> Unit,
    vararg val styles: String
) {
    LIGHT({ stage ->
        if (OS.get() == OS.WINDOWS) FXWinUtil.setDarkMode(stage, false)
    }, "/style/shared/shared.css", "/style/light/light.css"),
    DARK({ stage ->
        if (OS.get() == OS.WINDOWS) FXWinUtil.setDarkMode(stage, true)
    }, "/style/shared/shared.css", "/style/dark/dark.css");

    fun invokeHook(stage: Stage) = Platform.runLater { hook.invoke(stage) }
}
