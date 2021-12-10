package pro.fateeva.chuchasfilms.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.FilmRepositoryImpl
import pro.fateeva.chuchasfilms.data.PreferencesRepository
import pro.fateeva.chuchasfilms.data.PreferencesRepositoryImpl

class MainViewModel(
    private val filmRepository: FilmRepository = FilmRepositoryImpl(),
    private val preferencesRepository: PreferencesRepository = PreferencesRepositoryImpl()
) : ViewModel() {

    private val popularMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val nowPlayingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val upcomingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getFilmsFromRemoteSource(movieTopList: MovieTopList, context: Context, genre: GenreEnum) =
        getDataFromRemoteSource(movieTopList, context, genre)

    private fun getDataFromRemoteSource(
        movieTopList: MovieTopList,
        context: Context,
        genre: GenreEnum
    ) {
        val liveData = getMoviesData(movieTopList)

        liveData.value = AppState.Loading
        Thread {
            try {
                val filmList = filmRepository.getFilmsFromServer(movieTopList).filter {
                    val isAdultFromPreferences = getAdultFilmsPreference(context)
                    isAdultFromPreferences || it.isAdult == false
                }.filter {
                    genre == GenreEnum.ALL || it.genres?.contains(genre.value) == true
                }
                liveData.postValue(AppState.Success(filmList))
            } catch (e: Exception) {
                liveData.postValue(AppState.Error(e))
            }
        }.start()
    }

    fun getMoviesData(movieTopList: MovieTopList) = when (movieTopList) {
        MovieTopList.POPULAR -> popularMoviesLiveData
        MovieTopList.NOW_PLAYING -> nowPlayingMoviesLiveData
        MovieTopList.UPCOMING -> upcomingMoviesLiveData
    }

    fun saveAdultFilmsPreference(context: Context, value: Boolean) {
        preferencesRepository.saveAdultFilmsPreference(context, value)
    }

    fun getAdultFilmsPreference(context: Context): Boolean {
        return preferencesRepository.getAdultFilmsPreference(context)
    }

}