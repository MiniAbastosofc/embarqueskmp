package org.example.project.data.remote.response

import kotlinx.serialization.Serializable
import org.example.project.domain.model.PersonalBodegaModel

@Serializable
data class PersonalBodegaResponse(
    val checadores: List<PersonalBodegaModel>,
    val estibadores: List<PersonalBodegaModel>
) {
    fun toDomain(): PersonalBodegaLists {
        return PersonalBodegaLists(
            checadores = this.checadores,
            estibadores = this.estibadores
        )
    }
}

data class PersonalBodegaLists(
    val checadores: List<PersonalBodegaModel>,
    val estibadores: List<PersonalBodegaModel>
)