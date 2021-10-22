package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.FilmsLoader
import pro.fateeva.chuchasfilms.ui.main.Film

class FilmRepositoryImpl : FilmRepository {

    override fun getFilmsFromServer(): List<Film> {
        val dto = FilmsLoader.loadFilms()

        return dto?.results?.map { dto->
            Film(
                title = dto.title,
                year = dto.release_date.substring(0,4),
                rating = dto.vote_average.toString()
            )
        } ?: emptyList()
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