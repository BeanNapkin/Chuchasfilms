package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.Genre

class FilmMapper {

    fun map(filmListDTO: FilmListDTO?, genres: Map<Int, Genre>): List<Film> =
        filmListDTO?.results?.map { dto ->
            Film(
                id = dto.id,
                title = dto.title,
                isAdult = dto.adult,
                year = dto.release_date.substring(0, 4),
                rating = dto.vote_average.toString(),
                posterPath = dto.poster_path,
                description = dto.overview,
                genres = dto.genre_ids.mapNotNull { genreId -> genres[genreId]?.name }
            )
        } ?: emptyList()

    fun map(genreListDTO: GenreListDTO?): Map<Int, Genre> =
        genreListDTO?.genres?.map { it.id to Genre(it.name) }?.toMap() ?: emptyMap()
}
