package pro.fateeva.chuchasfilms.room

import androidx.room.*
import pro.fateeva.chuchasfilms.ui.main.Film

@Dao
interface FilmDetailsDao {

    @Query("SELECT * FROM FilmLocalData")
    fun all(): List<FilmLocalData>

    @Query("SELECT * FROM FilmLocalData WHERE id = :id")
    fun getById(id: Long): FilmLocalData?

    @Query("SELECT * FROM FilmLocalData WHERE isFavourite = 1")
    fun getByIsFavourite(): List<FilmLocalData>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FilmLocalData)

    @Update
    fun update(entity: FilmLocalData)

    @Delete
    fun delete(entity: FilmLocalData)
}