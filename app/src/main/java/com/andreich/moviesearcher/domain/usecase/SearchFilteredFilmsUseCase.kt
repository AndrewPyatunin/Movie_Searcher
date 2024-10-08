package com.andreich.moviesearcher.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFilteredFilmsUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun execute(
        pageSize: Int,
        requestId: String,
        name: String? = null,
        scope: CoroutineScope,
        filters: Map<String, List<String>> = emptyMap(),
        completeRequest: Boolean = false,
        sortFilters: Map<String, Int> = emptyMap()
    ): Flow<PagingData<Movie>> {
        return repository.searchFilteredFilms(
            pageSize,
            requestId,
            name,
            filters,
            completeRequest,
            sortFilters
        ).cachedIn(scope)
    }
}