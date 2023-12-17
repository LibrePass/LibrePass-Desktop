package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.entry.CopyCipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.entry.PasswordCipherEntry
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.types.cipher.Cipher

class LoginDataSection : CipherSection(tr("cipher.logindata.login")) {
    private val nameEntry = CipherEntry(tr("cipher.logindata.name"))
    private val usernameEntry = CopyCipherEntry(tr("cipher.logindata.username"))
    private val passwordEntry = PasswordCipherEntry(tr("cipher.logindata.password"))

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
            passwordEntry.setValue(cipher.loginData?.password!!, true)
        }
    }
}
