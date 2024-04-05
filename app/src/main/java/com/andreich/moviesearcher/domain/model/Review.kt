package com.andreich.moviesearcher.domain.model

data class Review(
    val id: Int = 0,
    val movieId: Int,
    val title: String,
    val type: String = "",
    val review: String,
    val date: String,
    val author: String
)
