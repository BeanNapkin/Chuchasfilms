package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.room.FilmDetailsDao
import pro.fateeva.chuchasfilms.room.FilmLocalData
import pro.fateeva.chuchasfilms.ui.main.Film
import java.util.*

class LocalRepositoryImpl(private val localDataSource: FilmDetailsDao) : LocalRepository {
    override fun getAllFilms(): List<FilmLocalData> {
        return localDataSource.all()
    }

    override fun saveEntity(id: Long, note: String?, isFavourite: Boolean?) {
        var film = localDataSource.getById(id)
        if (film != null) {
            if (note != null) {
                film.noteAboutFilm = note
            }
            if (isFavourite != null) {
                film.isFavourite = isFavourite
            }
            film.viewDate = Date()
            localDataSource.update(film)
        } else {
            film = FilmLocalData(id, note, Date(), isFavourite)
            localDataSource.insert(film)
        }
    }

    override fun getFilmById(id: Long): FilmLocalData? {
        return localDataSource.getById(id)
    }

    override fun getFilmsByIsFavourite(): List<FilmLocalData> {
        return localDataSource.getByIsFavourite() ?: emptyList()
    }
}