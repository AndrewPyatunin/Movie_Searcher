package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poster_detail")
data class PosterEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val height: Int,
    val movieId: Int,
    val previewUrl: String,
    val type: String,
    val width: Int,
    val page: Int
)
