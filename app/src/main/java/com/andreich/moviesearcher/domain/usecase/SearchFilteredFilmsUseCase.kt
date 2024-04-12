package com.andreich.moviesearcher.domain.usecase

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFilteredFilmsUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun execute(/*searchParams: String,*/ pageSize: Int, requestId: Long, name: String? = null): Flow<PagingData<Movie>> = repository.searchFilteredFilms(
//        searchParams,
        pageSize,
        requestId,
        name
    )
}