package com.andreich.moviesearcher.domain.usecase

import androidx.paging.*
import androidx.paging.testing.asSnapshot
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

const val MOVIE_NAME = "Троя"

class SearchFilteredFilmsUseCaseTest {

    val repository: MovieRepository = mock()
    val movie = Movie(
        100,
        MOVIE_NAME,
        type = "",
        year = 2001,
        ageRating = 16,
        url = "",
        previewUrl = "",
        isSeries = false,
        requestId = ""
    )
    val useCase by lazy { SearchFilteredFilmsUseCase(repository) }

    @Before
    fun setUp() {
        repository.stub {
            onBlocking {
                searchFilteredFilms(
                    pageSize = 1,
                    requestId = "/",
                    name = MOVIE_NAME,
                    filters = emptyMap(),
                    completeRequest = false,
                    sortFilters = emptyMap()
                )
            } doReturn flow {
                emit(PagingData.empty())
                emit(PagingData.from(listOf(movie)))
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun execute() = runTest {
        coroutineScope {
            val result = useCase.execute(
                pageSize = 10,
                name = MOVIE_NAME,
                filters = emptyMap(),
                completeRequest = false,
                sortFilters = emptyMap(),
                requestId = "",
                scope = TestScope(UnconfinedTestDispatcher())
            ).asSnapshot()
            assertEquals(
                listOf(movie), result
            )
        }

    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun execute() = runTest(UnconfinedTestDispatcher()) {
//        val resultFlow = useCase.execute(
//            pageSize = 1,
//            requestId = "/",
//            name = MOVIE_NAME,
//            scope = TestScope(UnconfinedTestDispatcher()),
//            filters = emptyMap(),
//            completeRequest = false,
//            sortFilters = emptyMap()
//        )
//
//        val resultList = mutableListOf<Movie>()
//        val job = launch {
//            resultList.addAll(resultFlow.asSnapshot())
//        }
//
//        advanceUntilIdle()
//        job.join()
//        assertEquals(listOf(movie), resultList)
//        job.cancel()
//    }
}