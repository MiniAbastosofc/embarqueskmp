package org.example.project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
//import org.example.project.createDataStore
import org.example.project.data.RepositoryImpl
import org.example.project.data.SessionRepositoryImpl
import org.example.project.data.local.datastore.PreferencesDataSource
import org.example.project.data.local.datastore.SessionRepository
import org.example.project.data.remote.ApiService
import org.example.project.domain.AgregarFotoUseCase
import org.example.project.domain.ClearSessionUseCase
import org.example.project.domain.CompletarEmbarqueUseCase
import org.example.project.domain.CrearIncidenciaUseCase
import org.example.project.domain.CrearUsuarioUseCase
import org.example.project.domain.GetAuthTokenUseCase
import org.example.project.domain.GetLoginStateUseCase
import org.example.project.domain.GetRutasUseCase
import org.example.project.domain.GetShipmentDetailsByDateUseCase
import org.example.project.domain.GetShipmentDetailsByIdUseCase
import org.example.project.domain.IniciarRutaUseCase
import org.example.project.domain.LoginUseCase
import org.example.project.domain.Repository
import org.example.project.domain.SaveSessionUseCase
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    coerceInputValues = true
                    ignoreUnknownKeys = true
                },
//                    contentType = ContentType.Any
                )
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTP
//                    host = "172.16.4.187"
                    //port = 3099
                    host = "172.16.3.213"
                    port = 3011

                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60_000   // 60s
                connectTimeoutMillis = 60_000
                socketTimeoutMillis = 60_000
            }
        }
    }
    // DataStore (nuevo)
//    single<DataStore<Preferences>> {
//        createDataStore()
//    }
    // DataSource (nuevo)
    singleOf(::PreferencesDataSource)

    factoryOf(::ApiService)
    factoryOf(::RepositoryImpl) {
        // Con .bindAs(), le dices a Koin:
        // "Cuando alguien pida 'Repository', dale esta instancia de 'RepositoryImpl'"
        binds(arrayOf(Repository::class).toList())
    }
    // Registro de la interfaz y la implementación del repositorio (Mejor práctica)
    // val repository: Repository by inject() // Esto se hace automáticamente con factoryOf(::RepositoryImpl)
    factoryOf(::SessionRepositoryImpl) {
        binds(arrayOf(SessionRepository::class).toList())
    }
    // Registro del caso de uso
    factoryOf(::LoginUseCase)
    factoryOf(::GetRutasUseCase)
    factoryOf(::IniciarRutaUseCase)
    factoryOf(::GetShipmentDetailsByDateUseCase)
    factoryOf(::GetShipmentDetailsByIdUseCase)
    factoryOf(::AgregarFotoUseCase)
    factoryOf(::CompletarEmbarqueUseCase)
    factoryOf(::GetLoginStateUseCase)
    factoryOf(::SaveSessionUseCase)
    factoryOf(::ClearSessionUseCase)
    factoryOf(::GetAuthTokenUseCase)
    factoryOf(::CrearUsuarioUseCase)
    factoryOf(::CrearIncidenciaUseCase)
}