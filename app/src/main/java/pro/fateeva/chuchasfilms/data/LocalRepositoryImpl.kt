package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.room.HistoryDao
import pro.fateeva.chuchasfilms.room.HistoryEntity
import pro.fateeva.chuchasfilms.ui.main.Film
import java.util.*

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {
    override fun getAllHistory(): List<HistoryEntity> {
        return localDataSource.all()
    }

    override fun saveEntity(id: Long, note: String?) {
        var historyEntity = localDataSource.getHistoryById(id)
        if (historyEntity != null) {
            if (note != null) {
                historyEntity.noteAboutFilm = note
            }
            historyEntity.viewDate = Date()
            localDataSource.update(historyEntity)
        } else {
            historyEntity = HistoryEntity(id, note, Date())
            localDataSource.insert(historyEntity)
        }
    }

    override fun getHistoryById(id: Long): HistoryEntity? {
        return localDataSource.getHistoryById(id)
    }
}