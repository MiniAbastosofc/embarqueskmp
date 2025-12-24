package org.example.project.di

import org.example.project.ui.core.SessionViewModel
import org.example.project.ui.home.tabs.admin.AdminViewModel
import org.example.project.ui.home.tabs.historial.HistorialViewModel
import org.example.project.ui.home.tabs.incidencias.IncidenciasViewModel
import org.example.project.ui.home.tabs.rutas.RutasViewModel
import org.example.project.ui.home.tabs.login.LoginViewModel
import org.example.project.ui.home.tabs.sucursales.SucursalesViewModel
import org.example.project.ui.home.tabs.user.UserViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::AdminViewModel)
    viewModelOf(::RutasViewModel)
    viewModelOf(::HistorialViewModel)
    viewModelOf(::SucursalesViewModel)
    viewModelOf(::SessionViewModel)
    viewModelOf(::UserViewModel)
    viewModelOf(::IncidenciasViewModel)

}