package com.andreich.moviesearcher.data.datasource.remote

import com.andreich.moviesearcher.domain.pojo.*

interface RemoteDataSource {

    suspend fun searchWithFilters(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        filters: Map<String, List<String>> = emptyMap(),
    ): RequestResultDto<MovieDto>

    suspend fun getActors(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<PersonDto>

    suspend fun searchFilm(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        movieName: String
    ): RequestResultDto<MovieDto>

    suspend fun getReviews(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<ReviewDto>

    suspend fun getSeasons(
        apiKey: String,
        page: Int = 1,
        limit: Int = 10,
        movieId: Int,
        vararg selectFields: String
    ): RequestResultDto<SeasonDto>

    suspend fun getPosters(
        apiKey: String,
        movieId: Int,
        page: Int = 1,
        limit: Int = 10,
    ): RequestResultDto<PosterDto>
}