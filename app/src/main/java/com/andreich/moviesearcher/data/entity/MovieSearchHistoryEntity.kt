package com.andreich.moviesearcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class MovieSearchHistoryEntity(
    @PrimaryKey
    val id: String,
    val movieTitle: String,
    val time: Long
)
