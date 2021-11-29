package pro.fateeva.chuchasfilms.app

import android.app.Application
import androidx.room.Room
import pro.fateeva.chuchasfilms.room.FilmDetailsDao
import pro.fateeva.chuchasfilms.room.FilmDataBase

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        private var appInstance: App? = null
        private var db: FilmDataBase? = null
        private const val DB_NAME = "Films.db"

        fun getFilmDetailsDao(): FilmDetailsDao {
            if (db == null) {
                synchronized(FilmDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            FilmDataBase::class.java,
                            DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }

            return db!!.filmDao()
        }
    }
}
