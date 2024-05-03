package com.andreich.moviesearcher.data.network

import com.andreich.moviesearcher.domain.pojo.*
import retrofit2.http.*

interface ApiService {

    @GET(
        "movie?selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=names" +
                "&selectFields=description&selectFields=slogan&selectFields=type&selectFields=isSeries" +
                "&selectFields=year&selectFields=rating&selectFields=ageRating&selectFields=votes" +
                "&selectFields=seasonsInfo&selectFields=movieLength&selectFields=seriesLength" +
                "&selectFields=totalSeriesLength&selectFields=genres&selectFields=countries" +
                "&selectFields=poster&selectFields=logo&selectFields=networks&selectFields=persons" +
                "&selectFields=sequelsAndPrequels&selectFields=top250" +
                "&notNullFields=year&notNullFields=ageRating&notNullFields=countries.name" +
                "&votes.kp=1000-6666666&status=!announced&status=!pre-production"
    )
    suspend fun searchWithFilters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(QUERY_COUNTRY) country: List<String> = emptyList(),
        @Query(QUERY_GENRE) genres: List<String> = emptyList(),
        @Query(QUERY_NETWORKS) network: List<String> = emptyList(),
        @Query(QUERY_MOVIE_TYPE) movie_type: List<String> = emptyList(),
        @Query(QUERY_YEAR) years: List<String> = emptyList(),
        @Query(QUERY_RATING) rating: List<String> = emptyList(),
        @QueryMap sortField: Map<String, String> = emptyMap(),
        @QueryMap sortType: Map<String, String> = emptyMap()
    ): RequestResultDto<MovieDto>

    @GET("person?selectFields=id&selectFields=name&selectFields=enName" +
            "&selectFields=photo&selectFields=sex&selectFields=birthday&selectFields=age" +
            "&selectFields=countAwards&selectFields=profession&sortField=countAwards" +
            "&sortField=movies.general&sortType=-1&sortType=-1")
    suspend fun getActors(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(QUERY_PARAM_MOVIE_ID) movieId: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg filters: String,
    ): RequestResultDto<PersonDto>

    @GET("movie/search")
    suspend fun searchFilm(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(QUERY_PARAM_MOVIE_SEARCH_NAME) movieName: String,
    ): RequestResultDto<MovieDto>

    @GET("review?selectFields=id&selectFields=movieId&selectFields=title" +
            "&selectFields=type&selectFields=review&selectFields=date&selectFields=author")
    suspend fun getReviews(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(REVIEW_PARAM_MOVIE_ID) movieId: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg filters: String,
    ): RequestResultDto<ReviewDto>

    @GET("season?selectFields=movieId&selectFields=poster&selectFields=number" +
            "&selectFields=name&selectFields=enName&selectFields=description" +
            "&selectFields=enDescription&selectFields=episodesCount&selectFields=airDate" +
            "&selectFields=episodes&sortField=airDate&sortField=episodes.date&sortType=1&sortType=1&number=1-30")
    suspend fun getSeasons(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(QUERY_PARAM_MOVIE_ID) movieId: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg selectFields: String
    ): RequestResultDto<SeasonDto>

    @GET("image?&type=cover&type=frame&type=promo&type=still&type=wallpaper")
    suspend fun getPosters(
        @Query("movieId") movieId: Int,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
    ): RequestResultDto<PosterDto>

    @GET("movie/{movieId}")
    suspend fun getFilmById(
        @Query("movieId") movieId: Int,
    )

    companion object {

        private const val QUERY_PARAM_SELECT_FIELDS = "selectFields"
        private const val QUERY_PARAM_MOVIE_ID = "movies.id"
        private const val QUERY_PARAM_MOVIE_SEARCH_NAME = "query"
        private const val REVIEW_PARAM_MOVIE_ID = "movieId"
    }
}