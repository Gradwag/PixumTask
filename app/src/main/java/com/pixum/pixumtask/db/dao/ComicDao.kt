package com.pixum.pixumtask.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pixum.pixumtask.db.entity.Comic

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Comic)

    @Delete
    suspend fun delete(note: Comic)

    @Query("SELECT * FROM comicTable")
    fun getAllComics(): LiveData<List<Comic>>

    @Update
    suspend fun update(note: Comic)
}