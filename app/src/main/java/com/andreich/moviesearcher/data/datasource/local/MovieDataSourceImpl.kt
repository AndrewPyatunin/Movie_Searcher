package com.andreich.moviesearcher.data.datasource.local

import androidx.paging.PagingSource
import com.andreich.moviesearcher.data.database.MovieDao
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieDataSource {

    override fun getMovies(
        requestId: String,
        genreFilter: String,
        countryFilter: String,
        movieTypeFilter: String,
        networkFilter: String,
        ratingFilter: Double?,
        yearStartFilter: Int?,
        yearEndFilter: Int?
    ): PagingSource<Int, MovieEntity> {
        if (requestId.trim().isEmpty()) return movieDao.loadMovies(
            requestId,
            genreFilter,
            countryFilter,
            movieTypeFilter,
            networkFilter,
            ratingFilter,
            yearStartFilter,
            yearEndFilter
        )
        return movieDao.getMovies(requestId)
    }

    override fun getMovie(id: Int): Flow<MovieEntity> {
        return movieDao.getMovie(id)
    }

    override suspend fun insertMovies(list: List<MovieEntity>) {
        movieDao.insertMovies(list)
    }
}