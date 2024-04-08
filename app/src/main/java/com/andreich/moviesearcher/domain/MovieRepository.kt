package com.andreich.moviesearcher.domain

import androidx.paging.PagingData
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Request
import com.andreich.moviesearcher.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getFilms(): Flow<List<Movie>>

    fun getMovie(movieId: Int): Flow<Movie>

    fun getReviews(movieId: Int): Flow<List<Review>>

    fun getPersons(movieId: Int): Flow<List<Person>>

    fun getRequests(): Flow<List<Request>>

    suspend fun fetchFilms()

    suspend fun searchFilteredFilms(requestParams: String, pageSize: Int, requestId: Long): Flow<PagingData<Movie>>

    suspend fun fetchFilmInfo(id: Int)

    suspend fun addRequest(request: Request)

    suspend fun fetchReviews(movieId: Int)

    suspend fun searchFilm(name: String)
}