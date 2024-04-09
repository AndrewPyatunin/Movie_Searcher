package com.andreich.moviesearcher.domain.usecase

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.repo.PosterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(
    private val repository: PosterRepository
) {

    fun execute(movieId: Int, pageSize: Int = 10): Flow<PagingData<Poster>> {
        return repository.getPosters(movieId, pageSize)
    }
}