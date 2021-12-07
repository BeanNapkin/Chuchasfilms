package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.rest_entities.FilmDTO
import pro.fateeva.chuchasfilms.rest_entities.FilmListDTO
import pro.fateeva.chuchasfilms.rest_entities.GenreListDTO
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.Genre

class FilmMapper {

    fun map(filmListDTO: FilmListDTO?, genres: Map<Int, Genre>): List<Film> =
        filmListDTO?.results?.map { dto -> mapFilm(dto, genres)
        } ?: emptyList()

    fun mapFilm(dto: FilmDTO, genres: Map<Int, Genre>) : Film =
        Film(
            id = dto.id,
            title = dto.title,
            isAdult = dto.adult,
            year = dto.release_date.substring(0, 4),
            rating = dto.vote_average.toString(),
            posterPath = dto.poster_path,
            description = dto.overview,
            genres = dto.genre_ids?.mapNotNull { genreId -> genres[genreId]?.name }
                ?: dto.genres?.map { it.name } ?: error("Genres not found for film ${dto.id}")
        )

    fun map(genreListDTO: GenreListDTO?): Map<Int, Genre> =
        genreListDTO?.genres?.map { it.id to Genre(it.name) }?.toMap() ?: emptyMap()
}
