package pro.fateeva.chuchasfilms.rest_entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class FilmDTO(
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String,
    val genre_ids: List<Int>
) : Serializable

data class  FilmListDTO(
    val page:  Int,
    val results: List<FilmDTO>
): Serializable
