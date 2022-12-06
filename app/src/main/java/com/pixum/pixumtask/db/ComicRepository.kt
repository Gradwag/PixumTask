package com.pixum.pixumtask.db

import androidx.lifecycle.LiveData
import com.pixum.pixumtask.db.dao.ComicDao
import com.pixum.pixumtask.db.entity.Comic

class ComicRepository(private val comicDao: ComicDao) {
    val allComics: LiveData<List<Comic>> = comicDao.getAllComics()

    suspend fun insert(note: Comic) {
        comicDao.insert(note)
    }

    suspend fun delete(note: Comic){
        comicDao.delete(note)
    }

    suspend fun update(note: Comic){
        comicDao.update(note)
    }
}