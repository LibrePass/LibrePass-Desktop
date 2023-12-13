package dev.medzik.librepass.desktop.gui.components.cipher.section

import dev.medzik.librepass.desktop.gui.components.cipher.entry.CopyCipherEntry
import dev.medzik.librepass.desktop.gui.components.cipher.section.CipherSection
import dev.medzik.librepass.desktop.locale.LangManager.tr
import dev.medzik.librepass.types.cipher.Cipher

class WebsiteSection : CipherSection(tr("dashboard.cipher.website")) {
    fun setCipher(cipher: Cipher) {
        clearAllEntries()

        var i = 1;
        for (uri in cipher.loginData?.uris!!) {
            val entry = CopyCipherEntry(tr("dashboard.cipher.url") + " $i")
            entry.setValue(uri)
            addEntry(entry)
            i++
        }
    }
}