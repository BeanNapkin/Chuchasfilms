package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.room.FilmLocalData

interface LocalRepository {
    fun getAllFilms(): List<FilmLocalData>
    fun saveEntity(id: Long, note: String? = null, isFavourite: Boolean? = null)
    fun getFilmById(id: Long) : FilmLocalData?
    fun getFilmsByIsFavourite() : List<FilmLocalData>
}