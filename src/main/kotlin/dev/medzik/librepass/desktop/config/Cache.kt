package dev.medzik.librepass.desktop.config

import java.io.File
import java.nio.file.Files

object Cache {
    private val cacheDir = File(Config.workDir,"cache");
    fun init() {
        if (!cacheDir.exists()) {
            if (!cacheDir.mkdir())
                throw RuntimeException("can't create work dir")
        }
    }

    private fun processName(name: String): String {
        return name.replace("https://","")
            .replace("http://","")
            .replace("/","_")
    }

    fun cacheExists(name: String,ext: String): Boolean {
        return File(cacheDir, processName("$name.$ext")).exists()
    }

    fun addCache(bytes: ByteArray,name: String,ext: String) {
        Files.write(File(cacheDir, processName("$name.$ext")).toPath(),bytes)
    }

    fun getCached(name: String,ext: String): File {
        return File(cacheDir, processName("$name.$ext"))
    }
}