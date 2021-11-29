package pro.fateeva.chuchasfilms.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FilmLocalData::class), version = 1, exportSchema = false)
abstract class FilmDataBase : RoomDatabase() {

    abstract fun filmDao(): FilmDetailsDao
}