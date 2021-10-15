package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.ui.main.Film

interface FilmRepository {
    fun getFilmsFromServer(): List<Film>
    fun getFilmsFromLocalStorage(): List<Film>
}