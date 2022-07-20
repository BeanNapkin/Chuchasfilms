package pro.fateeva.chuchasfilms.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.FilmsUseCase
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.FilmRepositoryImpl
import pro.fateeva.chuchasfilms.data.PreferencesRepository
import pro.fateeva.chuchasfilms.data.PreferencesRepositoryImpl

class MainViewModel(
    private val useCase:  FilmsUseCase = FilmsUseCase()
) : ViewModel() {

    private val popularMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val nowPlayingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val upcomingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getFilmsFromRemoteSource(movieTopList: MovieTopList, context: Context, genre: GenreEnum) {
        val liveData = getMoviesData(movieTopList)
        liveData.value = AppState.Loading
        Thread {
            try {
                liveData.postValue(AppState.Success(useCase.getDataFromRemoteSource(movieTopList, context, genre)))
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
        useCase.saveAdultFilmsPreference(context, value)
    }

    fun getAdultFilmsPreference(context: Context): Boolean {
        return useCase.getAdultFilmsPreference(context)
    }
}