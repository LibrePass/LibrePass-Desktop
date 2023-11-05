package dev.medzik.librepass.desktop.config

import dev.medzik.librepass.desktop.style.Style
import dev.medzik.librepass.types.cipher.EncryptedCipher
import java.util.*

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

data class UserSecrets(
    val privateKey: String,
    val secretKey: String
)

data class Settings(
    var theme: Style
)

data class StoreCipher(
    val id: UUID,
    val owner: UUID,
    var encryptedCipher: EncryptedCipher
)