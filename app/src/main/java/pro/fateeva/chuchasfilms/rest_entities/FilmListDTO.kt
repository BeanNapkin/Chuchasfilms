package pro.fateeva.chuchasfilms.rest_entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class FilmDTO(
    val id: Long,
    val title: String,
    val adult: Boolean,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String,
    val genre_ids: List<Int>? = null,
    val genres: List<GenreDTO>? = null
) : Serializable

data class  FilmListDTO(
    val page:  Int,
    val results: List<FilmDTO>
): Serializable
