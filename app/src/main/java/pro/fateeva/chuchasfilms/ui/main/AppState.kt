package pro.fateeva.chuchasfilms.ui.main

sealed class AppState {
    data class Success(val filmsList: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}