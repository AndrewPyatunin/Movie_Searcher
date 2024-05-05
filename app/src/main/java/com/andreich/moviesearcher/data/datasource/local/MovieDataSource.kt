package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {

    @Suppress("LongParameterList")
    fun getMovies(
        requestId: String,
        genreFilter: String,
        countryFilter: String,
        movieTypeFilter: String,
        networkFilter: String,
        ratingFilter: Double?,
        yearStartFilter: Int?,
        yearEndFilter: Int?
    ): PagingSource<Int, MovieEntity>

    fun getSortedMovies(
        requestId: String,
        yearSortAsc: Boolean?,
        ageSortAsc: Boolean?,
        countrySortAsc: Boolean?
    ): PagingSource<Int, MovieEntity>

    fun getMovie(id: Int): Flow<MovieEntity>

    suspend fun insertMovies(list: List<MovieEntity>)
}