package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.MovieRepository
import com.andreich.moviesearcher.domain.repo.ReviewRepository

class GetReviewsUseCase(
    private val repository: ReviewRepository
) {

    fun execute(movieId: Int, pageSize: Int) = repository.getReviews(movieId, pageSize)
}