package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class FetchReviews(
    private val repository: MovieRepository
) {

    suspend fun execute(movieId: Int) = repository.fetchReviews(movieId)
}