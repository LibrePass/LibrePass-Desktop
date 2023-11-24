package dev.medzik.librepass.desktop.config

import dev.medzik.librepass.client.api.CipherClient
import javafx.application.Platform
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.nio.file.Files
import java.util.concurrent.CompletableFuture

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

    fun cacheIcon(url: String, imageView: ImageView) {
        CompletableFuture.runAsync {
            synchronized(Cache) {
                if (cacheExists(url, "png")) {
                    val fis = FileInputStream(getCached(url, "png"))
                    Platform.runLater {
                        imageView.image = Image(fis)
                    }
                } else {
                    val urlStream = URI(CipherClient.getFavicon(domain = url)).toURL().openStream()
                    addCache(urlStream.readAllBytes()!!, url, "png")
                    val fis = FileInputStream(getCached(url, "png"))
                    Platform.runLater {
                        imageView.image = Image(fis)
                    }
                }
            }
        }
    }
}