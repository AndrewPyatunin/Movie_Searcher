package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class GetReviewsUseCase(
    private val repository: MovieRepository
) {

    fun execute(movieId: Int) = repository.getReviews(movieId)
}