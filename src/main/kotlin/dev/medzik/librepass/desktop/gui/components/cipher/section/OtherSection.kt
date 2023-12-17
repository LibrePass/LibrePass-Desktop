package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CopyCipherEntry
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.types.cipher.Cipher

class OtherSection : CipherSection(tr("cipher.logindata.other")) {
    private val notesEntry = CopyCipherEntry(tr("cipher.logindata.notes"))

    init {
        addEntry(notesEntry)
    }

    fun setCipher(cipher: Cipher) {
        notesEntry.setValue(cipher.loginData?.notes!!)
    }
}
