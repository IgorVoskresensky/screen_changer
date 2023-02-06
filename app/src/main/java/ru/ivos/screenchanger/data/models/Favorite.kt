package ru.ivos.screenchanger.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "previewURL")
    val previewURL: String,

    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String
)