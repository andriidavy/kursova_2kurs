package com.example.registration.datastore

interface DatastoreRepo {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun clearPreferences(key: String)
}