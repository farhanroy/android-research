package com.android.datastore.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.android.datastore.model.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/*
 * Handling user manager to write in data store
 * when user login or logout
 */
class UserManager (context: Context) {

    // Creating data store with name --> "user"
    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user")

    // Write in datastore when user is login
    suspend fun updateIsLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val isLoggedIn = preferences[IS_LOGGED_IN]?: false
            UserPreferences(isLoggedIn)
        }

    companion object {
        // PREFERENCE KEYS for datastore
        val IS_LOGGED_IN = preferencesKey<Boolean>("is_login")
    }
}