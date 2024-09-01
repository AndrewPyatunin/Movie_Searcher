package com.andreich.moviesearcher.data.repo

import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @Test
    fun getMovie() = runTest {
        val mockRepo: MovieRepository = mock()
        mockRepo.insertMovies(
            listOf(
                Movie(
                    10,
                    "Movie1",
                    type = "",
                    year = 2000,
                    slogan = "",
                    ageRating = 12,
                    genres = emptyList(),
                    countries = emptyList(),
                    url = "",
                    previewUrl = "",
                    actors = emptyList(),
                    votes = 1000,
                    network = emptyList(),
                    movieLength = 0,
                    isSeries = false,
                    seriesLength = 0,
                    page = 20,
                    requestId = "",
                    ratingImdb = 0.0
                )
            )
        )
        val movieFlow = flow { emit(Movie(
            10,
            "Movie1",
            type = "",
            year = 2000,
            slogan = "",
            ageRating = 12,
            genres = emptyList(),
            countries = emptyList(),
            url = "",
            previewUrl = "",
            actors = emptyList(),
            votes = 1000,
            network = emptyList(),
            movieLength = 0,
            isSeries = false,
            seriesLength = 0,
            page = 20,
            requestId = "",
            ratingImdb = 0.0
        )) }
        Mockito.`when`(mockRepo.getMovie(10)).thenReturn(movieFlow)
        assertEquals(mockRepo.getMovie(10).first().id, 10)
    }

    @Test
    fun searchFilteredFilms() {
    }

    @Test
    fun getMovieHistory() {
    }

    @Test
    fun searchFilm() {
    }
}