package com.andreich.moviesearcher.ui

data class ReviewItem(
    val id: Int,
    val movieId: Int,
    val title: String,
    val type: String,
    val review: CharSequence,
    val date: String,
    val author: String
)
