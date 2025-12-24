package org.example.project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.dsl.module

val androidDataStoreModule = module {
    single<DataStore<Preferences>> {
        get<android.content.Context>().dataStore
    }
}

// Property delegate para DataStore
private val android.content.Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "app_preferences"
)