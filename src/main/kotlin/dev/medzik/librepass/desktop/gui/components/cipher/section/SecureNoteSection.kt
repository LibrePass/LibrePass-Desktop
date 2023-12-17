package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.entry.CopyCipherEntry
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.types.cipher.Cipher

class SecureNoteSection : CipherSection(tr("cipher.securenote")) {
    private val titleEntry = CipherEntry(tr("cipher.securenote.title"))
    private val noteEntry = CopyCipherEntry(tr("cipher.securenote.notes"))

    init {
        addEntry(titleEntry)
        addEntry(noteEntry)
    }

    fun setCipher(cipher: Cipher) {
        titleEntry.setValue(cipher.secureNoteData?.title!!)
        noteEntry.setValue(cipher.secureNoteData?.note!!)
    }
}
