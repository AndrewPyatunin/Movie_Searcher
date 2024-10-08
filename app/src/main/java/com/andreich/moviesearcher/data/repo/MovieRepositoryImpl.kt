package com.andreich.moviesearcher.data.repo

import android.util.Log
import androidx.paging.*
import com.andreich.moviesearcher.data.database.MovieDatabase
import com.andreich.moviesearcher.data.database.MovieRemoteKeyDao
import com.andreich.moviesearcher.data.datasource.local.HistoryDataSource
import com.andreich.moviesearcher.data.datasource.local.MovieDataSource
import com.andreich.moviesearcher.data.datasource.remote.RemoteDataSource
import com.andreich.moviesearcher.data.entity.BookmarkMovieEntity
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity
import com.andreich.moviesearcher.data.entity.PersonEntity
import com.andreich.moviesearcher.data.mapper.EntityToModelMapper
import com.andreich.moviesearcher.data.mapper.MovieMapper
import com.andreich.moviesearcher.data.remotemediator.MovieRemoteMediator
import com.andreich.moviesearcher.domain.*
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.MovieSearchHistory
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.PersonDto
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val database: MovieDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val movieDataSource: MovieDataSource,
    private val historyDataSource: HistoryDataSource,
    private val apiKey: String,
    private val movieMapper: MovieMapper<MovieDto, MovieEntity>,
    private val personMapper: MovieMapper<PersonDto, PersonEntity>,
    private val movieEntityMapper: EntityToModelMapper<MovieEntity, Movie>,
    private val movieBookmarkEntityMapper: EntityToModelMapper<BookmarkMovieEntity, Movie>,
    private val remoteKeyDao: MovieRemoteKeyDao,
) : MovieRepository {

    override fun getMovie(movieId: Int): Flow<Movie> {
        return flow {
            var localMovie = movieDataSource.getMovie(movieId).firstOrNull()
            if (localMovie == null || localMovie.actors.isEmpty()) {
                val remoteMovie = remoteDataSource.getMovie(movieId)
                movieDataSource.insertMovies(listOf(movieMapper.map(remoteMovie, 0, "")))
                localMovie = movieDataSource.getMovie(movieId).firstOrNull()
            }
            if (localMovie == null) {
                throw IllegalArgumentException("Movie with id $movieId not found in database.")
            }
            emit(movieEntityMapper.map(localMovie))
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieBookmark(movieId: Int): Flow<Movie?> {
        return movieDataSource.getMovieBookmark(movieId).map {
            if (it != null) {
                movieBookmarkEntityMapper.map(it)
            } else null
        }
    }

    override fun getBookmarkMovies(): Flow<List<Movie>> {
        return movieDataSource.getBookmarkMovies().map {
            it.map { entity ->
                movieBookmarkEntityMapper.map(entity)
            }
        }
    }

    override suspend fun insertMovieBookmark(movie: Movie) {
        movieDataSource.insertMovieBookmark(
            with(movie) {
                BookmarkMovieEntity(
                    id,
                    name,
                    alternativeName,
                    type,
                    year,
                    slogan,
                    description,
                    rating,
                    ratingImdb,
                    ageRating,
                    genres,
                    countries,
                    countries[0],
                    url,
                    previewUrl,
                    actors.map {
                        PersonEntity(
                            it.id ?: 0,
                            it.photoUrl ?: "",
                            it.name ?: "",
                            it.enName ?: "",
                            it.profession ?: "",
                            it.enProfession ?: "",
                            it.description ?: "",
                            page
                        )
                    },
                    100,
                    network,
                    seasonsAmount,
                    top250,
                    movieLength ?: 0,
                    isSeries,
                    seriesLength ?: 0,
                    page,
                    requestId,
                )
            }
        )
    }

    override suspend fun removeMovieBookmark(movieId: Int) {
        movieDataSource.removeMovieBookmark(movieId)
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        movieDataSource.insertMovies(movies.map {
            with(it) {
                MovieEntity(
                    id,
                    name,
                    alternativeName,
                    type,
                    year,
                    slogan,
                    description,
                    rating,
                    ratingImdb,
                    ageRating,
                    genres,
                    countries,
                    countries[0],
                    url,
                    previewUrl,
                    actors.map {
                        PersonEntity(
                            it.id ?: 0,
                            it.photoUrl ?: "",
                            it.name ?: "",
                            it.enName ?: "",
                            it.profession ?: "",
                            it.enProfession ?: "",
                            it.description ?: "",
                            page
                        )
                    },
                    100,
                    network,
                    seasonsAmount,
                    top250,
                    movieLength ?: 0,
                    isSeries,
                    seriesLength ?: 0,
                    page,
                    requestId,
                    bookmark
                )
            }

        })
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchFilteredFilms(
        pageSize: Int,
        requestId: String,
        name: String?,
        filters: Map<String, List<String>>,
        completeRequest: Boolean,
        sortFilters: Map<String, Int>
    ): Flow<PagingData<Movie>> {
        Log.d("SEARCH_IN_REPO", "$pageSize")
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                if (sortFilters.isEmpty()) {
                    movieDataSource.getMovies(
                        requestId,
                        filters[QUERY_GENRE]?.get(0) ?: "",
                        filters[QUERY_COUNTRY]?.get(0) ?: "",
                        filters[QUERY_MOVIE_TYPE]?.get(0) ?: "",
                        filters[QUERY_NETWORKS]?.get(0) ?: "",
                        filters[QUERY_RATING]?.get(0)?.substringBefore('-')?.toDoubleOrNull(),
                        filters[QUERY_YEAR]?.get(0)?.substringBefore('-')?.toIntOrNull(),
                        filters[QUERY_YEAR]?.get(0)?.substringAfter('-')?.toIntOrNull()
                    )
                } else
                    movieDataSource.getSortedMovies(
                        requestId,
                        sortFilters[QUERY_YEAR]?.let { it == 1 },
                        sortFilters[QUERY_AGE_RATING]?.let { it == 1 },
                        sortFilters[QUERY_COUNTRY]?.let { it == 1 }
                    )

            },
            remoteMediator = MovieRemoteMediator(
                database,
                remoteDataSource,
                apiKey,
                remoteKeyDao = remoteKeyDao,
                movieMapper = movieMapper,
                personMapper = personMapper,
                name = name,
                requestId = requestId,
                filters = filters,
                completeRequest = completeRequest,
                sortFilter = mutableMapOf<String, String>().apply {
                    sortFilters.forEach {
                        Log.d("SORT_REPO", it.key)
                        when (it.key) {
                            QUERY_AGE_RATING -> {
                                this[QUERY_PARAM_SORT_FIELD] = QUERY_AGE_RATING
                                this[QUERY_PARAM_SORT_TYPE] = it.value.toString()
                            }
                            QUERY_COUNTRY -> {
                                this[QUERY_PARAM_SORT_FIELD] = QUERY_COUNTRY
                                this[QUERY_PARAM_SORT_TYPE] = it.value.toString()
                            }
                            QUERY_YEAR -> {
                                this[QUERY_PARAM_SORT_FIELD] = QUERY_YEAR
                                this[QUERY_PARAM_SORT_TYPE] = it.value.toString()
                            }
                            else -> {
                            }
                        }
                    }
                }
            )
        ).flow.map {
            it.map {
                try {
                    Log.d("SEARCH_IN_REPO_MAP", it.name)
                    movieEntityMapper.map(it)
                } catch (e: Exception) {
                    throw e
                }
            }
        }.catch { ex ->
            ex.printStackTrace()
        }
    }

    override suspend fun insertMovieHistory(request: MovieSearchHistory) {
        historyDataSource.insertRequest(
            MovieSearchHistoryEntity(
                request.id,
                request.movieTitle,
                System.currentTimeMillis()
            )
        )
    }

    override suspend fun getMovieHistory(): List<MovieSearchHistory> {
        return historyDataSource.getHistory().map {
            MovieSearchHistory(it.id, it.movieTitle)
        }
    }

    override suspend fun searchFilm(name: String) {
        TODO("Not yet implemented")
    }
}