package com.andreich.moviesearcher.data.entity

import androidx.room.Entity

@Entity(tableName = "poster_detail")
data class PosterDetailEntity(
    val url: String,
    val height: Int,
    val movieId: Int,
    val previewUrl: String,
    val type: String,
    val width: Int,
    val id: String
)
