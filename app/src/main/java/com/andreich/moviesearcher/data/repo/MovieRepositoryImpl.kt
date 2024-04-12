package com.andreich.moviesearcher.data.repo

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.MovieDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.MovieRemoteMediator
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonDto
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val movieDataSource: MovieDataSource,
    private val apiKey: String,
    private val movieMapper: MovieMapper<MovieDto, MovieEntity>,
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
    private val movieEntityMapper: EntityToModelMapper<MovieEntity, Movie>,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieRepository {

    override fun getMovie(movieId: Int): Flow<Movie> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchFilteredFilms(
//        requestParams: String,
        pageSize: Int,
        requestId: Long,
        name: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = { movieDataSource.getMovies(requestId) },
            remoteMediator = MovieRemoteMediator(
                database,
                remoteDataSource,
                apiKey,
                movieMapper = movieMapper,
                personMapper = personMapper,
                remoteKeyDao = remoteKeyDao,
                name = name,
                requestId = requestId
            ),
        ).flow.map {
            it.map {
                movieEntityMapper.map(it)
            }
        }
    }

    override suspend fun searchFilm(name: String) {
        TODO("Not yet implemented")
    }
}