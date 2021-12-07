package pro.fateeva.chuchasfilms.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.MovieTopList
import pro.fateeva.chuchasfilms.app.App
import pro.fateeva.chuchasfilms.data.*

class FavouriteFilmsViewModel(
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getFilmDetailsDao()),
    private val repository: FilmRepository = FilmRepositoryImpl()
) :    ViewModel() {

    private val favouriteMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getFavouriteFilms() {
        Thread {
            try{
                val favouriteFilms = localRepository.getFilmsByIsFavourite().map {
                    repository.getFilmById(it.id)
                }
                favouriteMoviesLiveData.postValue(AppState.Success(favouriteFilms))
            } catch (e: Exception){
                favouriteMoviesLiveData.postValue(AppState.Error(e))
            }
        }.start()
    }

    fun getfavouriteMoviesLiveData() = favouriteMoviesLiveData

}