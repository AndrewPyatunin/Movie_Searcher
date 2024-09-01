package com.andreich.moviesearcher.data.network

import android.util.Log
import com.andreich.moviesearcher.domain.pojo.MovieDto
import com.andreich.moviesearcher.domain.pojo.RequestResultDto
import com.andreich.moviesearcher.domain.repo.MovieRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private var server: MockWebServer = MockWebServer()
    private lateinit var api: ApiService
    private lateinit var repo: MovieRepository
    private val BASE_URL = "https://api.kinopoisk.dev/v1.4/"

    @Before
    fun setUp() {
        repo = mock()
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(ApiKeyInterceptor())
            .build()
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
        server.start(8080)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun searchWithFilters_with_success() = runTest {
        val dto = RequestResultDto<MovieDto>()
        Log.d("Test_search_dto", dto.toString())
        val json = GsonBuilder().create().toJson(dto)
        Log.d("Test_search_json", json)
        val response = MockResponse().apply {
            setBody(json)
        }
        server.enqueue(response)
        val data = api.searchWithFilters()
        Log.d("Test_search_success", data.pages.toString())
        // Await the result before proceeding with assertions
        server.takeRequest()
        assertEquals(data, dto)
    }

    @Test
    fun searchWithFilters_with_error() = runTest {
        val response = MockResponse().apply {
            setResponseCode(404)
        }
        server.enqueue(response)

        assertThrows(Exception::class.java) {
            repo.searchFilteredFilms(10, "", null, emptyMap(), false, emptyMap())
            server.takeRequest()
        }
    }

    @Test
    fun getActors() {
    }

    @Test
    fun searchFilm() {
    }

    @Test
    fun getReviews() {
    }

    @Test
    fun getSeasons() {
    }

    @Test
    fun getPosters() {
    }

    @Test
    fun getFilmById() {
    }
}