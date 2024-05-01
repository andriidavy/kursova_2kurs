package com.example.registration.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.registration.datastore.Constants.DATASTORE_NAME
import com.example.registration.ui.login.data.UserDTO
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreRepoImpl @Inject constructor(
    private val context: Context
) : DatastoreRepo {

    override suspend fun putUser(key: String, user: UserDTO) {
        val preferenceKey = stringPreferencesKey(key)
        val jsonString = Gson().toJson(user)
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = jsonString
        }
    }

    override suspend fun getUser(key: String): UserDTO? {
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val jsonString = context.dataStore.data.firstOrNull()?.get(preferenceKey)
            jsonString?.let {
                Gson().fromJson(it, UserDTO::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

//    override suspend fun putInt(key: String, value: Int) {
//        val preferenceKey = intPreferencesKey(key)
//        context.dataStore.edit {
//            it[preferenceKey] = value
//        }
//    }

    override suspend fun getInt(key: String): Int? {
        return try {
            val preferenceKey = intPreferencesKey(key)
            val preference = context.dataStore.data.first()
            preference[preferenceKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun clearPreferences(key: String) {
        val preferenceKey = intPreferencesKey(key)
        context.dataStore.edit {
            if (it.contains(preferenceKey)) {
                it.remove(preferenceKey)
            }
        }
    }
}