package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.rest_entities.GenreDTO
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.Genre

interface FilmRepository {
    fun getGenresFromServer(): Map<Int, Genre>
    fun getFilmsFromServer(): List<Film>
    fun getFilmsFromLocalStorage(): List<Film>
}