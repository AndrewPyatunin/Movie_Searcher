package com.andreich.moviesearcher.data.repo

import android.util.Log
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
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_COUNTRY
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_GENRE
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_MOVIE_TYPE
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_NETWORKS
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_RATING
import com.andreich.moviesearcher.ui.screen.FilterFragment.Companion.QUERY_YEAR
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
        return movieDataSource.getMovie(movieId).map {
            movieEntityMapper.map(it)
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchFilteredFilms(
        requestParams: String?,
        pageSize: Int,
        requestId: String,
        name: String?,
        filters: Map<String, List<String>>,
    ): Flow<PagingData<Movie>> {
        Log.d("SEARCH_IN_REPO", "$pageSize")
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                initialLoadSize = pageSize,
            ),
            pagingSourceFactory = {
                movieDataSource.getMovies(
                    requestId,
                    filters[QUERY_GENRE]?.get(0) ?: "",
                    filters[QUERY_COUNTRY]?.get(0) ?: "",
                    filters[QUERY_MOVIE_TYPE]?.get(0) ?: "",
                    filters[QUERY_NETWORKS]?.get(0) ?: "",
                    filters[QUERY_RATING]?.get(0)?.toDoubleOrNull(),
                    filters[QUERY_YEAR]?.get(0)?.substringBefore('-')?.toIntOrNull(),
                    filters[QUERY_YEAR]?.get(0)?.substringAfter('-')?.toIntOrNull()
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
                filters = filters
            )
        ).flow.map {
            it.map {
                Log.d("SEARCH_IN_REPO_MAP", it.name)
                movieEntityMapper.map(it)
            }
        }
    }

    override suspend fun searchFilm(name: String) {
        TODO("Not yet implemented")
    }
}