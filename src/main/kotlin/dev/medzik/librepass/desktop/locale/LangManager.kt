package dev.medzik.librepass.desktop.locale

import java.util.*

object LangManager {

    private lateinit var currentLocale: ResourceBundle

    fun init(): ResourceBundle {
        currentLocale = ResourceBundle.getBundle("/locales/locale", Locale.getDefault())
        return currentLocale
    }

    fun getLocale() = currentLocale

    fun tr(name: String): String = currentLocale.getString(name)
}
