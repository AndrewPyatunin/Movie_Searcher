package com.andreich.moviesearcher.data.datasource.remote

import com.andreich.moviesearcher.data.network.ApiService
import com.andreich.moviesearcher.domain.pojo.*

class RemoteDataSourceImpl(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun searchWithFilters(
        apiKey: String,
        page: Int,
        limit: Int,
        sortFilters: Map<String, String>,
        vararg filters: String
    ): RequestResultDto<MovieDto> {
        return apiService.searchWithFilters(apiKey, page, limit, sortFilters, *filters)
    }

    override suspend fun getActors(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<PersonsDto> {
        return apiService.getActors(apiKey, page, limit, movieId, *filters)
    }

    override suspend fun searchFilm(
        apiKey: String,
        page: Int,
        limit: Int,
        movieName: String
    ): RequestResultDto<MovieDto> {
        return apiService.searchFilm(apiKey, page, limit, movieName)
    }

    override suspend fun getReviews(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<ReviewDto> {
        return apiService.getReviews(apiKey, page, limit, movieId, *filters)
    }

    override suspend fun getSeasons(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg selectFields: String
    ): RequestResultDto<SeasonsDto> {
        return apiService.getSeasons(apiKey, page, limit, movieId, *selectFields)
    }

    override suspend fun getPosters(
        apiKey: String,
        movieId: Int,
        page: Int,
        limit: Int
    ): RequestResultDto<PosterDetailDto> {
        return apiService.getPosters(apiKey, movieId, page, limit)
    }
}