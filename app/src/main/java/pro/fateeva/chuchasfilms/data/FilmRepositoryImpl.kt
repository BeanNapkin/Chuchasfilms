package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.FilmsLoader
import pro.fateeva.chuchasfilms.ui.main.Film

class FilmRepositoryImpl : FilmRepository {

    private val mapper = FilmMapper()

    override fun getFilmsFromServer(): List<Film> = mapper.map(FilmsLoader.loadFilms())
        .map {
            it.copy(posterPath = FilmsLoader.generateImagePath(it.posterPath ?: ""))
        }

    override fun getFilmsFromLocalStorage(): List<Film> {
        val filmsList: List<Film> = listOf(
            Film(
                title = "5й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
            Film(
                title = "6й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
            Film(
                title = "7й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
            Film(
                title = "8й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
            Film(
                title = "9й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
            Film(
                title = "10й элемент",
                year = "2000",
                rating = "95",
                genre = "Боевик",
                description = "Бла-бла-бла",
                cast = "Брюс"
            ),
        )
        return filmsList
    }
}