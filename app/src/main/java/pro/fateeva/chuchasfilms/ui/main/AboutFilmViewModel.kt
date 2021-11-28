package pro.fateeva.chuchasfilms.ui.main

import androidx.lifecycle.ViewModel
import pro.fateeva.chuchasfilms.app.App
import pro.fateeva.chuchasfilms.data.LocalRepository
import pro.fateeva.chuchasfilms.data.LocalRepositoryImpl
import pro.fateeva.chuchasfilms.room.HistoryEntity

class AboutFilmViewModel(
    private val localRepository: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())
) :    ViewModel() {

    fun saveNote(film: Film, note: String){
        localRepository.saveEntity(id = film.id, note = note)
    }

    fun saveFilmToViewed(id: Long){
        localRepository.saveEntity(id)
    }

    fun getHistory(id: Long) : HistoryEntity? {
       return localRepository.getHistoryById(id)
    }
}