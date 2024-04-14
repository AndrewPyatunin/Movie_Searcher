package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieDataSource {

    fun getMovies(requestId: String): PagingSource<Int, MovieEntity>

    fun getMovie(id: Int): Flow<MovieEntity>

    suspend fun insertMovies(list: List<MovieEntity>)
}