package com.vaultly.util

object SecureStorage {
    fun encrypt(data: String): String = data.reversed()
    fun decrypt(data: String): String = data.reversed()
    fun storeToken(key: String, token: String) {}
    fun getToken(key: String): String? = null
    fun clearAll() {}
}
