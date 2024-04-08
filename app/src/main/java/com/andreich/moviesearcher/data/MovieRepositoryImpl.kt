package com.andreich.moviesearcher.data

import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.database.RemoteKeyDao
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.domain.MovieRepository
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Request
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.domain.pojo.MovieDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val apiKey: String,
    private val movieMapper: MovieMapper<MovieDto, MovieEntity>,
    private val movieEntityMapper: EntityToModelMapper<MovieEntity, Movie>,
    private val remoteKeyDao: MovieRemoteKeyDao
) : MovieRepository {

    override fun getFilms(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getMovie(movieId: Int): Flow<Movie> {
        TODO("Not yet implemented")
    }

    override fun getReviews(movieId: Int): Flow<List<Review>> {
        TODO("Not yet implemented")
    }

    override fun getPersons(movieId: Int): Flow<List<Person>> {
        TODO("Not yet implemented")
    }

    override fun getRequests(): Flow<List<Request>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchFilms() {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun searchFilteredFilms(requestParams: String, pageSize: Int, requestId: Long): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = { database.movieDao().getMovies(requestId) },
            remoteMediator = MovieRemoteMediator(
                database,
                remoteDataSource,
                apiKey,
                movieMapper = movieMapper,
                remoteKeyDao = remoteKeyDao
            ),
        ).flow.map {
            it.map {
                movieEntityMapper.map(it)
            }
        }
    }

    override suspend fun fetchFilmInfo(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addRequest(request: Request) {
        TODO("Not yet implemented")
    }

    override suspend fun fetchReviews(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun searchFilm(name: String) {
        TODO("Not yet implemented")
    }
}