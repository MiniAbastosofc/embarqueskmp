package org.example.project

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// Necesitas pasar el Context desde Android
//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")
//
//actual fun createDataStore(): DataStore<Preferences> {
//    // Esto se configurará en el Android App class
//    throw NotImplementedError("DataStore debe ser proporcionado via Koin en Android")
//}