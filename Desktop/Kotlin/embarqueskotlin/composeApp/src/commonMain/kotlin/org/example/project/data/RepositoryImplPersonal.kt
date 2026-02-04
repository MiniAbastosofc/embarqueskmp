package org.example.project.data

import org.example.project.data.remote.ApiServicesPersonal
import org.example.project.data.remote.response.PersonalBodegaLists
import org.example.project.domain.RepositoryPersonal
import org.example.project.domain.model.PersonalBodegaModel


class RespositoryImplPersonal(
    val api: ApiServicesPersonal
) : RepositoryPersonal {

    override suspend fun estibadoresChecadores(): PersonalBodegaLists {
        val response = api.obtenerPersonalBodega()
        return response.toDomain()
    }
}
