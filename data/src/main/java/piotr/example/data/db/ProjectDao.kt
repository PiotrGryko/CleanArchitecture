package piotr.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBooks(book: List<ProjectEntity>)

    @Query("SELECT * FROM project")
    suspend fun getSavedBooks(): List<ProjectEntity>

    @Query("SELECT * FROM project where id=:id")
    suspend fun getSavedBookById(id:Long): ProjectEntity?

}