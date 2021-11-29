package pro.fateeva.chuchasfilms.ui.main

import pro.fateeva.chuchasfilms.room.FilmLocalData

sealed class AboutState {
    data class Success(val filmLocalData: FilmLocalData?) : AboutState()
    data class Error(val error: Throwable) : AboutState()
}