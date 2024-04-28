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
                "&selectFields=sequelsAndPrequels&selectFields=top250"
    )
    suspend fun searchWithFilters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10,
        @Query(QUERY_COUNTRY) country: List<String> = emptyList(),
        @Query(QUERY_GENRE) genres: List<String> = emptyList(),
        @Query(QUERY_NETWORKS) network: List<String> = emptyList(),
        @Query(QUERY_MOVIE_TYPE) movie_type: List<String> = emptyList(),
        @Query(QUERY_YEAR) years: List<String> = emptyList(),
        @Query(QUERY_RATING) rating: List<String> = emptyList()
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
        private const val QUERY_PARAM_SORT_FIELD = "sortField"
        private const val QUERY_PARAM_SORT_TYPE = "sortType"
        private const val QUERY_PARAM_MOVIE_ID = "movies.id"
        private const val QUERY_PARAM_MOVIE_SEARCH_NAME = "query"
        private const val PARAM_YEAR = "year"
        private const val PARAM_NAME = "name"
        private const val PARAM_RATING = "rating"
        private const val PARAM_ALT_NAME = "alternativeName"
        private const val PARAM_SLOGAN = "slogan"
        private const val PARAM_TYPE = "type"
        private const val PARAM_AGE_RATING = "ageRating"
        private const val PARAM_VOTES = "votes"
        private const val PARAM_GENRES = "genres"
        private const val PARAM_DESCRIPTION = "description"
        private const val PARAM_COUNTRIES = "countries"
        private const val PARAM_POSTER = "poster"
        private const val PARAM_LOGO = "logo"
        private const val PARAM_NETWORKS = "networks"
        private const val PARAM_PERSONS = "persons"
        private const val PARAM_MOVIE_LENGTH = "movieLength"
        private const val PARAM_IS_SERIES = "isSeries"
        private const val PARAM_TOTAL_SERIES_LENGTH = "totalSeriesLength"
        private const val PARAM_SERIES_LENGTH = "seriesLength"
        private const val PARAM_TOP250 = "top250"

        private const val SORT_TYPE_DOWN = "1"
        private const val SORT_TYPE_UP = "-1"

        private const val SEASON_PARAM_MOVIE_ID = "movieId"
        private const val SEASON_PARAM_POSTER = "poster"
        private const val SEASON_PARAM_NUMBER = "number"
        private const val SEASON_PARAM_NAME = "name"
        private const val SEASON_PARAM_EN_NAME = "enName"
        private const val SEASON_PARAM_DESCRIPTION = "description"
        private const val SEASON_PARAM_EN_DESCRIPTION = "enDescription"
        private const val SEASON_PARAM_EPISODES_COUNT = "episodesCount"
        private const val SEASON_PARAM_AIR_DATE = "airDate"
        private const val SEASON_PARAM_EPISODES = "episodes"
        private const val SEASON_SORT_PARAM_1 = "airDate"
        private const val SEASON_SORT_PARAM_2 = ""
        private const val SEASON_NUMBER_RESTRICTION = "1-30"

        private const val REVIEW_PARAM_REVIEW = "review"
        private const val REVIEW_PARAM_DATE = "date"
        private const val REVIEW_PARAM_TYPE = "type"
        private const val REVIEW_PARAM_AUTHOR = "author"
        private const val REVIEW_PARAM_ID = "id"
        private const val REVIEW_PARAM_MOVIE_ID = "movieId"
        private const val REVIEW_PARAM_TITLE = "title"
    }
}