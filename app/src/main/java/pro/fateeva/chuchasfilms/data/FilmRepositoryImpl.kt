package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.FilmsLoader
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.Genre

class FilmRepositoryImpl : FilmRepository {

    private val mapper = FilmMapper()

    override fun getGenresFromServer(): Map<Int, Genre> = mapper.map(FilmsLoader.loadGenres())

    override fun getFilmsFromServer(movieTopList: MovieTopList): List<Film> =
        mapper.map(FilmsLoader.loadFilms(movieTopList), getGenresFromServer())
            .map {
                it.copy(posterPath = FilmsLoader.generateImagePath(it.posterPath ?: ""))
            }

    override fun getFilmById(id: Long): Film =
        mapper.mapFilm(
            FilmsLoader.loadFilmById(id) ?: error("Film with id $id not found"),
            getGenresFromServer()
        )
            .let { it.copy(posterPath = FilmsLoader.generateImagePath(it.posterPath ?: "")) }
}