package pro.fateeva.chuchasfilms.ui.main

import pro.fateeva.chuchasfilms.room.HistoryEntity

sealed class AboutState {
    data class Success(val historyEntity: HistoryEntity?) : AboutState()
    data class Error(val error: Throwable) : AboutState()
}