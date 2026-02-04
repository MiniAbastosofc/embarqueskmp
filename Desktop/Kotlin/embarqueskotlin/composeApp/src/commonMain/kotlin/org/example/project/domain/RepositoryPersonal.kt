package org.example.project.domain

import org.example.project.data.remote.response.PersonalBodegaLists

interface RepositoryPersonal {
    suspend fun estibadoresChecadores(): PersonalBodegaLists
}