package org.example.project.di


import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.data.RepositoryImpl
import org.example.project.data.RepositoryImplProductividad
import org.example.project.data.RespositoryImplEmbarques
import org.example.project.data.RespositoryImplPausa
import org.example.project.data.RespositoryImplPersonal
import org.example.project.data.SessionRepositoryImpl
import org.example.project.data.local.datastore.PreferencesDataSource
import org.example.project.data.local.datastore.SessionRepository
import org.example.project.data.remote.ApiService
import org.example.project.data.remote.ApiServicePausas
import org.example.project.data.remote.ApiServicesEmbarques
import org.example.project.data.remote.ApiServicesIncidencias
import org.example.project.data.remote.ApiServicesPersonal
import org.example.project.data.remote.ApiServicesProductividad
import org.example.project.data.remote.RepositoryImplIncidencias
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
import org.example.project.domain.RepositoryEmbarques
import org.example.project.domain.RepositoryIncidencias
import org.example.project.domain.RepositoryPausas
import org.example.project.domain.RepositoryPersonal
import org.example.project.domain.RepositoryProductividad
import org.example.project.domain.SaveSessionUseCase
import org.example.project.domain.useCases.BuscarEmbarqueUseCase
import org.example.project.domain.useCases.FinalizarPausaUseCase
import org.example.project.domain.useCases.GetIncidenciasUseCase
import org.example.project.domain.useCases.GetProductividadUseCase
import org.example.project.domain.useCases.IniciarPausaUseCase
import org.example.project.domain.useCases.ObtenerPersonalBodegaUseCase
import org.example.project.domain.useCases.VerificarPausaActivaUseCase
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    json = Json {
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
//                    port = 3099
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
    factoryOf(::ApiServicePausas)
    factoryOf(::ApiServicesEmbarques)
    factoryOf(::ApiServicesPersonal)
    factoryOf(::ApiServicesProductividad)
    factoryOf(::ApiServicesIncidencias)
    factoryOf(::RepositoryImpl) {
        // Con .bindAs(), le dices a Koin:
        // "Cuando alguien pida 'Repository', dale esta instancia de 'RepositoryImpl'"
        binds(arrayOf(Repository::class).toList())
    }
    factoryOf(::RespositoryImplPausa)
    {
        binds(arrayOf(RepositoryPausas::class).toList())
    }
    factoryOf(::RespositoryImplEmbarques) {
        binds(arrayOf(RepositoryEmbarques::class).toList())
    }
    factoryOf(::RespositoryImplPersonal) {
        binds(arrayOf(RepositoryPersonal::class).toList())
    }
    factoryOf(::RepositoryImplProductividad) {
        binds(arrayOf(RepositoryProductividad::class).toList())
    }
    factoryOf(::RepositoryImplIncidencias) {
        binds(arrayOf(RepositoryIncidencias::class).toList())
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
    factoryOf(::IniciarPausaUseCase)
    factoryOf(::BuscarEmbarqueUseCase)
    factoryOf(::ObtenerPersonalBodegaUseCase)
    factoryOf(::VerificarPausaActivaUseCase)
    factoryOf(::FinalizarPausaUseCase)
    factoryOf(::GetProductividadUseCase)
    factoryOf(::GetIncidenciasUseCase)

}