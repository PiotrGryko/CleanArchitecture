package piotr.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProjectEntity::class], version = 1)
@TypeConverters(DbConverters::class)
abstract class ProjectsRoomDatabase : RoomDatabase() {
    abstract fun bookDao(): ProjectDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context,
            ProjectsRoomDatabase::class.java,
            "db").fallbackToDestructiveMigration().build()
    }
}