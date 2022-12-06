package com.pixum.pixumtask.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "comicTable", indices = [Index("title", unique = true)])
class Comic(
    @ColumnInfo(name = "title")val title: String,
    @ColumnInfo(name = "description")val description: String,
    @ColumnInfo(name = "thumbnail_path")val thumbnailPath: String
) {
    @PrimaryKey(autoGenerate = true) var id = 0
}