package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.MovieDao
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieDataSource {

    override fun getMovies(requestId: Long): PagingSource<Int, MovieEntity> {
        return movieDao.getMovies()
    }

    override fun getMovie(id: Int): Flow<MovieEntity> {
        return movieDao.getMovie(id)
    }

    override suspend fun insertMovies(list: List<MovieEntity>) {
        movieDao.insertMovies(list)
    }
}