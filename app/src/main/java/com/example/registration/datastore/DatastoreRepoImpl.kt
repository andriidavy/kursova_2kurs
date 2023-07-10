package com.example.registration.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.registration.datastore.Constants.DATASTORE_NAME
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

class DataStoreRepoImpl @Inject constructor(
    private val context: Context
) : DatastoreRepo {
    override suspend fun putString(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[preferenceKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val preference = context.dataStore.data.first()
            preference[preferenceKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun clearPreferences(key: String) {
        val preferenceKey = stringPreferencesKey(key)
        context.dataStore.edit {
            if (it.contains(preferenceKey)) {
                it.remove(preferenceKey)
            }
        }
    }

}