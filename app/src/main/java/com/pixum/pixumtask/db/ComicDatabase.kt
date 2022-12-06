package com.pixum.pixumtask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pixum.pixumtask.db.dao.ComicDao
import com.pixum.pixumtask.db.entity.Comic
import javax.inject.Singleton

@Singleton
@Database(entities = [Comic::class], version = 1, exportSchema = false)
abstract class ComicDatabase : RoomDatabase() {
    abstract fun getNotesDao(): ComicDao

    companion object {
        fun getDatabase(context: Context): ComicDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ComicDatabase::class.java,
                "comic_database"
            ).build()
        }
    }
}