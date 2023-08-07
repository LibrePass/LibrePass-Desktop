package dev.medzik.librepass.desktop.utils.windows

import com.sun.jna.*
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef.*
import com.sun.jna.platform.win32.WinNT
import javafx.stage.Stage

object FXWinUtil {
    private fun getNativeHandleForStage(stage: Stage): HWND {
        val searchString = "stage_" + java.util.UUID.randomUUID()
        val title = stage.title
        stage.title = searchString
        val hwnd = User32.INSTANCE.FindWindow(null, searchString)
        stage.title = title
        if (hwnd != null) {
            return hwnd
        } else throw RuntimeException("Can't find native window handle")
    }

    fun setDarkMode(stage: Stage, darkMode: Boolean) {
        val hwnd = getNativeHandleForStage(stage)
        val dwmapi = DwmSupport.INSTANCE
        val darkModeRef = BOOLByReference(BOOL(darkMode))
        dwmapi.DwmSetWindowAttribute(hwnd, 20, darkModeRef, Native.getNativeSize(BOOLByReference::class.java))
        forceRedrawOfWindowTitleBar(stage)
    }

    private fun forceRedrawOfWindowTitleBar(stage: Stage) {
        val minimalized = stage.isIconified
        stage.isIconified = !minimalized
        stage.isIconified = minimalized
    }

    private interface DwmSupport : Library {
        fun DwmSetWindowAttribute(
            hwnd: HWND?,
            dwAttribute: Int,
            pvAttribute: PointerType?,
            cbAttribute: Int
        ): WinNT.HRESULT

        companion object {
            val INSTANCE: DwmSupport = Native.load("dwmapi", DwmSupport::class.java)
        }
    }
}
