package pro.fateeva.chuchasfilms

import android.content.Context
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.FilmRepositoryImpl
import pro.fateeva.chuchasfilms.data.PreferencesRepository
import pro.fateeva.chuchasfilms.data.PreferencesRepositoryImpl
import pro.fateeva.chuchasfilms.ui.main.Film
import pro.fateeva.chuchasfilms.ui.main.GenreEnum

class FilmsUseCase(
    private val filmRepository: FilmRepository = FilmRepositoryImpl(),
    private val preferencesRepository: PreferencesRepository = PreferencesRepositoryImpl()
) {

    fun getDataFromRemoteSource(movieTopList: MovieTopList, context: Context, genre: GenreEnum) : List<Film> {
        val isAdultFromPreferences = getAdultFilmsPreference(context)
        val filmList = filterOnIsAdult(filmRepository.getFilmsFromServer(movieTopList), isAdultFromPreferences)
        return filterByGenre(filmList, genre)
    }

    fun saveAdultFilmsPreference(context: Context, value: Boolean) {
        preferencesRepository.saveAdultFilmsPreference(context, value)
    }

    fun getAdultFilmsPreference(context: Context): Boolean {
        return preferencesRepository.getAdultFilmsPreference(context)
    }

    companion object{
        fun filterOnIsAdult(filmList: List<Film>, includeAdult: Boolean) = filmList.filter {
            includeAdult || it.isAdult == false
        }

        fun filterByGenre(filmList: List<Film>, genre: GenreEnum) = filmList.filter {
            genre == GenreEnum.ALL || it.genres?.contains(genre.value) == true
        }
    }
}