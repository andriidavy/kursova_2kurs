package com.example.registration.datastore

interface DatastoreRepo {
    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String): Int?
    suspend fun clearPreferences(key: String)
}