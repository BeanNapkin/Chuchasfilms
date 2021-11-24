package pro.fateeva.chuchasfilms.rest_entities

import java.io.Serializable

data class GenreDTO(
    val id: Int,
    val name: String
) : Serializable

data class  GenreListDTO(
    val genres: List<GenreDTO>
): Serializable

