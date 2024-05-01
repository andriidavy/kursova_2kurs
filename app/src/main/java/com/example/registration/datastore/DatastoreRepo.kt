package com.example.registration.datastore

import com.example.registration.ui.login.data.UserDTO

interface DatastoreRepo {
    suspend fun putUser(key: String, user: UserDTO)
    suspend fun getUser(key: String): UserDTO?
    suspend fun clearPreferences(key: String)

    suspend fun getInt(key: String): Int?
}