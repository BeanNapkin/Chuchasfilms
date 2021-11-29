package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.room.FilmDetailsDao
import pro.fateeva.chuchasfilms.room.FilmLocalData
import java.util.*

class LocalRepositoryImpl(private val localDataSource: FilmDetailsDao) : LocalRepository {
    override fun getAllFilms(): List<FilmLocalData> {
        return localDataSource.all()
    }

    override fun saveEntity(id: Long, note: String?) {
        var film = localDataSource.getById(id)
        if (film != null) {
            if (note != null) {
                film.noteAboutFilm = note
            }
            film.viewDate = Date()
            localDataSource.update(film)
        } else {
            film = FilmLocalData(id, note, Date())
            localDataSource.insert(film)
        }
    }

    override fun getFilmById(id: Long): FilmLocalData? {
        return localDataSource.getById(id)
    }
}