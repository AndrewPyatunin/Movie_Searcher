package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.ReviewRepository
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {

    fun execute(movieId: Int, pageSize: Int = 10, requestId: String = "") = repository.getReviews(movieId, pageSize, requestId)
}