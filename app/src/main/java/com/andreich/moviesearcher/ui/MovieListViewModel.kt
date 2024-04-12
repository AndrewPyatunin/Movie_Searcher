package com.andreich.moviesearcher.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val searchMovieUseCase: SearchFilteredFilmsUseCase
) : ViewModel() {

    fun getData(): Flow<PagingData<Movie>> {
        return searchMovieUseCase.execute(10, System.currentTimeMillis())
    }

    fun searchFilm(name: String): Flow<PagingData<Movie>> {
        return searchMovieUseCase.execute(10, System.currentTimeMillis(), name)
    }
}