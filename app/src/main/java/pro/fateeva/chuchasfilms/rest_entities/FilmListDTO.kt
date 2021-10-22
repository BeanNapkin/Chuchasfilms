package pro.fateeva.chuchasfilms.rest_entities

import java.util.*

data class FilmDTO(
    val title: String,
    val release_date: String,
    val vote_average: Double,
)

data class  FilmListDTO(
    val page:  Int,
    val results: List<FilmDTO>
)
