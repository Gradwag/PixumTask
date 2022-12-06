package com.pixum.pixumtask.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pixum.pixumtask.db.entity.Comic

@Dao
interface ComicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comic : Comic)

    @Delete
    suspend fun delete(comic: Comic)

    @Query("SELECT * FROM comicTable")
    fun getAllComics(): LiveData<List<Comic>>

    @Update
    suspend fun update(comic: Comic)
}