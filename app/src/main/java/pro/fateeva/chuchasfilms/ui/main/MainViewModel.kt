package pro.fateeva.chuchasfilms.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.data.FilmRepository
import pro.fateeva.chuchasfilms.data.FilmRepositoryImpl
import java.lang.Thread.sleep
import kotlin.random.Random

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: FilmRepository = FilmRepositoryImpl()
) :
    ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getFilmsFromLocalSource() = getDataFromLocalSource()

    fun getFilmsFromRemoteSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(1000)
            randomState()
        }.start()
    }

    private fun randomState(){
        val isError = Random.nextBoolean()
        if (isError){
            liveDataToObserve.postValue(AppState.Error(RuntimeException()))
        } else {
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmsFromLocalStorage()))
        }
    }
}