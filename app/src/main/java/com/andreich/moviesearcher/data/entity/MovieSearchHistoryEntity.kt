package com.andreich.moviesearcher.data.entity

import androidx.room.Entity

@Entity("history")
data class MovieSearchHistoryEntity(
    val id: Int,
    val movieTitle: String,
    val movies: List<MovieEntity>,
)
