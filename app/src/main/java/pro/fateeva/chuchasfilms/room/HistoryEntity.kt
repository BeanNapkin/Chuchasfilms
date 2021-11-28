package pro.fateeva.chuchasfilms.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class HistoryEntity(
    @PrimaryKey
    val id: Long,
    var noteAboutFilm: String?,
    var viewDate: Date?
)
