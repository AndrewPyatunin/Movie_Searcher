package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

const val ID = 1001

class GetMovieUseCaseTest {

    val repository: MovieRepository = mock()
    val useCase by lazy { GetMovieUseCase(repository) }
    val movie = Movie(
        name = "Movie",
        type = "",
        year = 2000,
        slogan = "",
        ageRating = 12,
        genres = emptyList(),
        countries = emptyList(),
        url = "",
        previewUrl = "",
        votes = 100,
        actors = emptyList(),
        network = emptyList(),
        movieLength = 120,
        isSeries = false,
        seriesLength = null,
        page = 1,
        requestId = "",
        ratingImdb = 0.0
    )

    @Before
    fun setUp() {
        repository.stub { onBlocking { getMovie(ID) } doReturn flow { emit(movie) } }
    }

    @Test
    fun execute() = runTest {
        assertEquals(movie, useCase.execute(ID).first())
    }
}