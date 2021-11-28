package pro.fateeva.chuchasfilms.ui.main

sealed class AppState { // Как ENUM, только можно добавить перечисление классов
    data class Success(val filmsList: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}