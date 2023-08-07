package dev.medzik.librepass.desktop.config

import com.google.gson.Gson
import dev.medzik.librepass.desktop.style.Style
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
object Config {
    val workDir = File(getWorkDir("librepass"))

    fun init() {
        if (!workDir.exists()) {
            if (!workDir.mkdir())
                throw RuntimeException("can't create work dir")
        }

        if (workDir.exists()) {
            // write default settings
            if (!isObjectExists("settings"))
                writeObject(
                    "settings",
                    Settings(
                        theme = Style.LIGHT
                    )
                )
        }
    }

    private fun getWorkDir(name: String): String {
        val osname = System.getProperty("os.name").lowercase(Locale.getDefault())
        if (osname.startsWith("windows")) return Paths.get(System.getenv("APPDATA"), name)
            .toString() else if (osname.contains("nux") || osname.contains("freebsd")) return Paths.get(
            System.getProperty("user.home"),
            ".config",
            name
        ).toString() else if (osname.contains("mac") || osname.contains("darwin")) return Paths.get(
            System.getProperty("user.home"),
            "Library",
            "Application Support",
            name
        ).toString()
        return ""
    }

    fun writeObject(name: String, obj: Any) {
        val file = File(workDir, "$name.json")
        val serialized = Gson().toJson(obj)

        Files.writeString(file.toPath(), serialized)
    }

    inline fun <reified T> readObject(name: String): T {
        val file = File(workDir, "$name.json")
        val json = Files.readString(file.toPath())

        return Gson().fromJson(json, T::class.java)
    }

    inline fun <reified T : Any> overrideObject(name: String, obj: (T) -> T) {
        val readed = readObject<T>(name)
        writeObject(name, obj.invoke(readed))
    }

    fun isObjectExists(name: String): Boolean {
        return File(workDir, "$name.json").exists()
    }

    fun deleteObject(name: String) {
        File(workDir, "$name.json").delete()
    }
}

data class Credentials(
    val userId: UUID,
    val email: String,

    val apiUrl: String? = null,
    val apiKey: String,
    val publicKey: String,

    val lastSync: Long? = null,

    // argon2id parameters
    val memory: Int,
    val iterations: Int,
    val parallelism: Int,
)

data class DataStoreUserSecrets(
    val privateKey: String,
    val secretKey: String
)

data class Settings(
    var theme: Style
)
