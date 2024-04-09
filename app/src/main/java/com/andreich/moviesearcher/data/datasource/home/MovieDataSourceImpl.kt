package com.andreich.moviesearcher.data.datasource.home

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.MovieDao
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

class MovieDataSourceImpl(
    private val movieDao: MovieDao
) : MovieDataSource {

    override fun getMovies(requestId: Long): PagingSource<Int, MovieEntity> {
        return movieDao.getMovies(requestId)
    }

    override fun getMovie(id: Int): Flow<MovieEntity> {
        return movieDao.getMovie(id)
    }

    override suspend fun insertMovies(list: List<MovieEntity>) {
        movieDao.insertMovies(list)
    }
}