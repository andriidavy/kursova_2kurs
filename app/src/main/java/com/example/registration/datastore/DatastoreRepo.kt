package com.example.registration.datastore

interface DatastoreRepo {
    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun clearIntPreferences(key: String)
    suspend fun clearStringPreferences(key: String)
}