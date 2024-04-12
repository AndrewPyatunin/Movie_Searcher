package com.andreich.moviesearcher.domain.repo

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Person
import com.andreich.moviesearcher.domain.model.Request
import com.andreich.moviesearcher.domain.model.Review
import dagger.Binds
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMovie(movieId: Int): Flow<Movie>

    suspend fun searchFilm(name: String)

    fun searchFilteredFilms(
//        requestParams: String,
        pageSize: Int,
        requestId: Long,
        name: String?
    ): Flow<PagingData<Movie>>

}