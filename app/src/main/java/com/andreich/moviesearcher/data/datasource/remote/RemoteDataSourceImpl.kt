package com.andreich.moviesearcher.data.datasource.remote

import com.andreich.moviesearcher.data.network.*
import com.andreich.moviesearcher.domain.pojo.*
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun searchWithFilters(
        apiKey: String,
        page: Int,
        limit: Int,
        filters: Map<String, List<String>>,
        sortField: Map<String, String>,
        sortType: Map<String, String>
    ): RequestResultDto<MovieDto> {
        return apiService.searchWithFilters(
            page = page,
            limit = limit,
            country = filters[QUERY_COUNTRY] ?: emptyList(),
            genres = filters[QUERY_GENRE] ?: emptyList(),
            network = filters[QUERY_NETWORKS] ?: emptyList(),
            movie_type = filters[QUERY_MOVIE_TYPE] ?: emptyList(),
            years = filters[QUERY_YEAR] ?: emptyList(),
            rating = filters[QUERY_RATING] ?: emptyList(),
            sortField = sortField,
        )
    }

    override suspend fun getActors(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<PersonDto> {
        return apiService.getActors(page, limit, movieId, *filters)
    }

    override suspend fun searchFilm(
        apiKey: String,
        page: Int,
        limit: Int,
        movieName: String
    ): RequestResultDto<MovieDto> {
        return apiService.searchFilm(page, limit, movieName)
    }

    override suspend fun getReviews(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg filters: String
    ): RequestResultDto<ReviewDto> {
        return apiService.getReviews(page, limit, movieId, *filters)
    }

    override suspend fun getSeasons(
        apiKey: String,
        page: Int,
        limit: Int,
        movieId: Int,
        vararg selectFields: String
    ): RequestResultDto<SeasonDto> {
        return apiService.getSeasons(page, limit, movieId, *selectFields)
    }

    override suspend fun getPosters(
        apiKey: String,
        movieId: Int,
        page: Int,
        limit: Int
    ): RequestResultDto<PosterDto> {
        return apiService.getPosters(movieId, page, limit)
    }
}