package dev.medzik.librepass.desktop.gui.components.cipher

import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.types.cipher.Cipher

class LoginDataSection : CipherSection(tr("dashboard.cipher.login")) {
    private val nameEntry = CipherEntry(tr("dashboard.cipher.name"))
    private val usernameEntry = CopyCipherEntry(tr("dashboard.cipher.username"))
    private val passwordEntry = PasswordCipherEntry(tr("dashboard.cipher.password"))
    init {
        addEntry(nameEntry)
        addEntry(usernameEntry)
        addEntry(passwordEntry)
    }

    fun setCipher(cipher: Cipher) {
        clearAllEntries()

        if (!cipher.loginData?.name.isNullOrEmpty()) {
            addEntry(nameEntry)
            nameEntry.setValue(cipher.loginData?.name!!)
        }

        if (!cipher.loginData?.username.isNullOrEmpty()) {
            addEntry(usernameEntry)
            usernameEntry.setValue(cipher.loginData?.username!!)
        }

        if (!cipher.loginData?.password.isNullOrEmpty()) {
            addEntry(passwordEntry)
            passwordEntry.setValue(cipher.loginData?.password!!,true)
        }
    }
}