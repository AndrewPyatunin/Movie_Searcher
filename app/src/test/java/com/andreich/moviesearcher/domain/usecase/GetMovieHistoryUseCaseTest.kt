package com.andreich.moviesearcher.domain.usecase

import androidx.paging.PagingData
import com.andreich.moviesearcher.data.datasource.local.HistoryDataSource
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.MovieSearchHistory
import com.andreich.moviesearcher.domain.repo.MovieRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub

class MyRepo(
    private val historyDataSource: HistoryDataSource
): MovieRepository {

    override fun getMovie(movieId: Int): Flow<Movie> {
        TODO("Not yet implemented")
    }

    override fun getMovieBookmark(movieId: Int): Flow<Movie?> {
        TODO("Not yet implemented")
    }

    override fun getBookmarkMovies(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieBookmark(movie: Movie) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMovieBookmark(movieId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovies(movies: List<Movie>) {
        TODO("Not yet implemented")
    }

    override suspend fun searchFilm(name: String) {
        TODO("Not yet implemented")
    }

    override fun searchFilteredFilms(
        pageSize: Int,
        requestId: String,
        name: String?,
        filters: Map<String, List<String>>,
        completeRequest: Boolean,
        sortFilters: Map<String, Int>
    ): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMovieHistory(request: MovieSearchHistory) {
        historyDataSource.insertRequest(MovieSearchHistoryEntity(request.id, request.movieTitle, System.currentTimeMillis()))
    }

    override suspend fun getMovieHistory(): List<MovieSearchHistory> {
        return historyDataSource.getHistory().map {
            MovieSearchHistory(it.id, it.movieTitle)
        }
    }

}

class GetMovieHistoryUseCaseTest {

    val repository: MovieRepository = mock()
    val repo: MyRepo = MyRepo(mock())
    val useCase: GetMovieHistoryUseCase by lazy { GetMovieHistoryUseCase(repository) }
    val listHistory = listOf(MovieSearchHistory("id01", "Movie1"), MovieSearchHistory("id02", "Movie2"))

    @Before
    fun setup() {
        repository.stub { onBlocking { getMovieHistory() } doReturn listHistory }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun execute() = runTest(UnconfinedTestDispatcher()) {
//        launch { repo.insertMovieHistory(listHistory[0]) }
//        launch { repo.insertMovieHistory(listHistory[1]) }
//        assertNotNull(useCase)
//        advanceUntilIdle()
        assertEquals(listHistory, useCase.execute())
    }
}