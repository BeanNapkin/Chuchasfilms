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
) :    ViewModel() {

    private val popularMoviesLiveData: MutableLiveData<AppState> = MutableLiveData() // livedata - канал новостей. Он делает из Appstate - объект для наблюдения, чтобы потом пинались те, кто подписан на изменения
    private val nowPlayingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData() // Для каждого списка - свой канал новостей
    private val upcomingMoviesLiveData: MutableLiveData<AppState> = MutableLiveData()

    fun getFilmsFromRemoteSource(movieTopList: MovieTopList, context: Context) = getDataFromRemoteSource(movieTopList, context)

    private fun getDataFromRemoteSource(movieTopList: MovieTopList, context: Context) {
        val liveData = getMoviesData(movieTopList) // Какой список будем скачивать и в какой AppState складывать результат скачивания

        liveData.value = AppState.Loading // AppState - состояние загрузки
        Thread { // Отдельный поток, в котором будет загрузка фильмов
            try{
                val filmList = filmRepository.getFilmsFromServer(movieTopList).filter{ // Скачиваем спискок фильмов + фильтр для фильмов насчёт 18+
                    val isAdultFromPreferences = getAdultFilmsPreference(context)
                    if (isAdultFromPreferences == false && it.isAdult == true){ // Если в настройках нет галочки и фильм для взрослых
                        false // То выкидываем его из списка
                    } else {
                        true // В остальных всех случаях фильм остается в списке
                    }
                }
                liveData.postValue(AppState.Success(filmList)) // Фильмы скачались успешно, кладем в AppState список
            } catch (e: Exception){
                liveData.postValue(AppState.Error(e)) // Передаем ошибку, если фильмы не скачались, также в AppState
            }
        }.start()
    }

    fun getMoviesData(movieTopList: MovieTopList) = when(movieTopList) {
        MovieTopList.POPULAR -> popularMoviesLiveData
        MovieTopList.NOW_PLAYING -> nowPlayingMoviesLiveData
        MovieTopList.UPCOMING -> upcomingMoviesLiveData
    }

    fun saveAdultFilmsPreference(context: Context, value : Boolean){
        preferencesRepository.saveAdultFilmsPreference(context, value)
    }

    fun getAdultFilmsPreference(context: Context) : Boolean{
        return preferencesRepository.getAdultFilmsPreference(context)
    }

}