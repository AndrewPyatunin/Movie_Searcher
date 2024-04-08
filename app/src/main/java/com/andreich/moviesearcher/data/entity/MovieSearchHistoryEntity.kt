package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class MovieSearchHistoryEntity(
    @PrimaryKey
    val id: Long,
    val movieTitle: String,
//    val query: Map<String, String>,
    val movies: List<MovieEntity>,
)
