package com.alpharishi.passwordmanagerassignment


import android.annotation.SuppressLint
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object EncryptionUtil {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES"

    private val secretKey: SecretKey

    init {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(128)
        secretKey = keyGenerator.generateKey()
    }

    @SuppressLint("GetInstance")
    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    @SuppressLint("GetInstance")
    fun decrypt(encryptedInput: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.decode(encryptedInput, Base64.DEFAULT))
        return String(decryptedBytes)
    }
}