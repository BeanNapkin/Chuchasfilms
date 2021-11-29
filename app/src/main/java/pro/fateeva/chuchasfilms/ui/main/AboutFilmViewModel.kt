package pro.fateeva.chuchasfilms.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.app.App
import pro.fateeva.chuchasfilms.data.LocalRepository
import pro.fateeva.chuchasfilms.data.LocalRepositoryImpl

class AboutFilmViewModel(
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())
) :    ViewModel() {

    private val historyLiveData: MutableLiveData<AboutState> = MutableLiveData()

    fun saveNote(film: Film, note: String){
        localRepository.saveEntity(id = film.id, note = note)
    }

    fun saveFilmToViewed(id: Long){
        localRepository.saveEntity(id)
    }

    fun getHistory(id: Long) {
        Thread {
            try{
                val historyEntity = localRepository.getHistoryById(id)
                historyLiveData.postValue(AboutState.Success(historyEntity))
            } catch (e: Exception){
                historyLiveData.postValue(AboutState.Error(e))
            }
        }.start()
    }

    fun getHistoryLiveData() = historyLiveData
}