package com.viewBindingTemplate.remote

import org.apache.commons.codec.binary.Hex
import org.json.JSONObject
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESUtil {
    const val SEK = "sek"
    const val HASH = "hash"
    const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val UTC = "UTC"
    private val CHARACTER_ENCODING = Charsets.UTF_8
    private const val ENCRYPTION_IV = "D904363DB8DACEB8"
    private val iv by lazy { ENCRYPTION_IV.toByteArray(CHARACTER_ENCODING) }
    private val cipher by lazy { Cipher.getInstance("AES/CBC/PKCS5Padding") }
    private val ivParameterSpec by lazy { IvParameterSpec(iv) }
    fun secKeyEncryptWithAppKey(string: String): JSONObject?/*Map<String, String>?*/ {
        return try {
            val json = JSONObject(string)
            json.put("appKey", System.currentTimeMillis().formatTo())
            val key = ByteArray(32)
            SecureRandom().nextBytes(key)
            val randomString = json.toString()
                .toByteArray(CHARACTER_ENCODING)/*timeStamp in UTC*///getRandomString(32)
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), ivParameterSpec)
            val encrypted = cipher.doFinal(randomString)
            /* val result = HashMap<String, String>()
             result[SEK] = String(Hex.encodeHex(encrypted))
             result[HASH] = String(Hex.encodeHex(key))
             result*/
            val result = JSONObject()
            result.put(SEK, String(Hex.encodeHex(encrypted)))
            result.put(HASH, String(Hex.encodeHex(key)))
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun secKeyEncryptWithAppKey(): Map<String, String>? {
        return try {
            val key = ByteArray(32)
            SecureRandom().nextBytes(key)
            val randomString = System.currentTimeMillis().formatTo()?.toByteArray(CHARACTER_ENCODING)/*timeStamp in UTC*///getRandomString(32)
            cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), ivParameterSpec)
            val encrypted = cipher.doFinal(randomString)
            val result = HashMap<String, String>()
            result[SEK] = String(Hex.encodeHex(encrypted))
            result[HASH] = String(Hex.encodeHex(key))
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun Long.formatTo(outputFormat: String = UTC_FORMAT): String? {
        return try {
            SimpleDateFormat(outputFormat, Locale.ENGLISH).apply {
                timeZone = TimeZone.getTimeZone(UTC)
            }.format(Date(this))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length).map { allowedChars.random() }.joinToString("")
    }

    fun secKeyDecryptedWithSek(hashMap: Map<String, String>): String? {
        return try {
            val key = hashMap["appKey"]?.hexStringToByteArray() ?: return null
            val toDecode = hashMap["encryptedData"]?.hexStringToByteArray() ?: return null
            cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), ivParameterSpec)
            String(cipher.doFinal(toDecode))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun String.hexStringToByteArray(): ByteArray {
        val len = length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] =
                ((Character.digit(get(i), 16) shl 4) + Character.digit(get(i + 1), 16)).toByte()
            i += 2
        }
        return data
    }
}