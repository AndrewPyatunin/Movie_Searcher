package com.andreich.moviesearcher.data.network

import retrofit2.http.Query

const val QUERY_COUNTRY = "countries.name"
const val QUERY_GENRE = "genres.name"
const val QUERY_NETWORKS = "networks.items.name"
const val QUERY_MOVIE_TYPE = "type"
const val QUERY_YEAR = "year"
const val QUERY_RATING = "rating.kp"

data class MovieSearchFilters(
    @Query(QUERY_COUNTRY) val country: List<String>,
    @Query(QUERY_GENRE) val genres: List<String>,
    @Query(QUERY_NETWORKS) val network: List<String>,
    @Query(QUERY_MOVIE_TYPE) val movie_type: List<String>,
    @Query(QUERY_YEAR) val years: List<String>,
    @Query(QUERY_RATING) val rating: List<String>,
)
