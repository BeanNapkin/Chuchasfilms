package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.rest_entities.GenreDTO
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.Genre

interface FilmRepository {
    fun getGenresFromServer(): Map<Int, Genre>
    fun getFilmsFromServer(movieTopList: MovieTopList): List<Film>
    fun getFilmById(id: Long) : Film
}