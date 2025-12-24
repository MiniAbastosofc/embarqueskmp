package org.example.project

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import org.example.project.di.androidDataStoreModule
import org.example.project.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import java.io.File

class EmbarqueskotlinApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@EmbarqueskotlinApp)

            // Solo agregar el módulo de Android para DataStore
            modules(androidDataStoreModule)
        }
    }
}