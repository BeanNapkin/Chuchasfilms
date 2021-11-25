package pro.fateeva.chuchasfilms.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.FilmRepositoryImpl

class MainViewModel(
    private val repositoryImpl: FilmRepository = FilmRepositoryImpl()
) :    ViewModel() {

    private val popularMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val nowPlayingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val upcomingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getFilmsFromRemoteSource(movieTopList: MovieTopList) = getDataFromRemoteSource(movieTopList)

    private fun getDataFromRemoteSource(movieTopList: MovieTopList) {

        val liveData = getMoviesData(movieTopList)

        liveData.value = AppState.Loading
        Thread {
            try{
                liveData.postValue(AppState.Success(repositoryImpl.getFilmsFromServer(movieTopList)))
            } catch (e: Exception){
                liveData.postValue(AppState.Error(e))
            }
        }.start()
    }

    fun getMoviesData(movieTopList: MovieTopList) = when(movieTopList) {
        MovieTopList.POPULAR -> popularMoviesLiveData
        MovieTopList.NOW_PLAYING -> nowPlayingMoviesLiveData
        MovieTopList.UPCOMING -> upcomingMoviesLiveData
    }

}