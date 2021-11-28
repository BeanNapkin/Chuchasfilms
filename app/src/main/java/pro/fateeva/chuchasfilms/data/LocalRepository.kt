package pro.fateeva.chuchasfilms.data

import pro.fateeva.chuchasfilms.room.HistoryEntity
import pro.fateeva.chuchasfilms.ui.main.Film

interface LocalRepository {
    fun getAllHistory(): List<HistoryEntity>
    fun saveEntity(id: Long, note: String? = null)
    fun getHistoryById(id: Long) : HistoryEntity?
}