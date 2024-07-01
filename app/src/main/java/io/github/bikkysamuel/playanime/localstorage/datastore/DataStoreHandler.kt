package io.github.bikkysamuel.playanime.localstorage.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.bikkysamuel.playanime.utils.Constants
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreHandler @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_SETTINGS)

    private suspend fun <T> savePreferencesData(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit {settings ->
            settings[key] = value
        }
    }

    private suspend fun <T> loadPreferencesData(preferencesKey: Preferences.Key<T>): T? {
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    // String
    private suspend fun saveStringData(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        savePreferencesData(dataStoreKey, value)
    }

    private suspend fun loadStringData(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        return loadPreferencesData(dataStoreKey)
    }

    // Theme
    suspend fun saveSelectedThemeInDataStore(themeName: String) {
        saveStringData(Constants.DATASTORE_SELECTED_THEME, themeName)
    }

    suspend fun loadSelectedThemeInDataStore(): String? {
        return loadStringData(Constants.DATASTORE_SELECTED_THEME)
    }

    // Font Style
    suspend fun saveSelectedFontStyleInDataStore(fontName: String) {
        saveStringData(Constants.DATASTORE_SELECTED_FONT_STYLE, fontName)
    }

    suspend fun loadSelectedFontStyleInDataStore(): String? {
        return loadStringData(Constants.DATASTORE_SELECTED_FONT_STYLE)
    }
}