package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.MovieSearchHistory
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(movieId: Int): Flow<Movie>

    suspend fun insertMovies(movies: List<Movie>)

    suspend fun searchFilm(name: String)

    fun searchFilteredFilms(
        pageSize: Int,
        requestId: String,
        name: String?,
        filters: Map<String, List<String>>,
        completeRequest: Boolean,
        sortFilters: Map<String, Int>
    ): Flow<PagingData<Movie>>

    suspend fun insertMovieHistory(request: MovieSearchHistory)

    suspend fun getMovieHistory(): List<MovieSearchHistory>
}