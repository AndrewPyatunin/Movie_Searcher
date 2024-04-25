package com.andreich.moviesearcher.domain.usecase

import androidx.paging.cachedIn
import com.andreich.moviesearcher.domain.repo.ReviewRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetReviewsUseCase @Inject constructor(
    private val repository: ReviewRepository
) {

    fun execute(movieId: Int, pageSize: Int = 10, requestId: String = "", scope: CoroutineScope) = repository.getReviews(movieId, pageSize, requestId).cachedIn(scope)
}