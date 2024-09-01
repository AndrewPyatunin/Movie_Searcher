package com.andreich.moviesearcher.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.repo.PosterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(
    private val repository: PosterRepository
) {

    fun execute(movieId: Int, requestId: String ="", pageSize: Int = 10, scope: CoroutineScope): Flow<PagingData<Poster>> {
        return repository.getPosters(movieId, pageSize, requestId).cachedIn(scope)
    }
}