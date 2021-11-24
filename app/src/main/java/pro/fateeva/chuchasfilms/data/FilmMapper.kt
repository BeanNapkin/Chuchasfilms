package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.ui.main.Film

class FilmMapper {

    fun map(filmListDTO: FilmListDTO?): List<Film> =
        filmListDTO?.results?.map { dto ->
            Film(
                title = dto.title,
                year = dto.release_date.substring(0, 4),
                rating = dto.vote_average.toString(),
                posterPath = dto.poster_path
            )
        } ?: emptyList()
    }
