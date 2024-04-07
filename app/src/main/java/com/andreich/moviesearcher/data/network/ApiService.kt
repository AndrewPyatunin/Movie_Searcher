package com.andreich.moviesearcher.data.network

import retrofit2.http.FieldMap
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //Можно отфильтровать выдачу по году, стране и возрастному рейтингу
    //Если для пользователя есть возможность установить фильтры: по жанру, по стране производства,
    // по типу контента (сериал/фильм), по году выхода, по рейтингу Кинопоиска (от конкретного значения), поиск по сети производства (HBO,  Netflix  и т.п.)

    @GET(
        "movie?selectFields=id&selectFields=name&selectFields=alternativeName&selectFields=names" +
                "&selectFields=description&selectFields=slogan&selectFields=type&selectFields=isSeries" +
                "&selectFields=year&selectFields=rating&selectFields=ageRating&selectFields=votes" +
                "&selectFields=seasonsInfo&selectFields=movieLength&selectFields=seriesLength" +
                "&selectFields=totalSeriesLength&selectFields=genres&selectFields=countries" +
                "&selectFields=poster&selectFields=logo&selectFields=networks&selectFields=persons" +
                "&selectFields=sequelsAndPrequels&selectFields=top250" +
                "&type=tv-series&year=2010-2020&genres.name=%2B%D1%82%D1%80%D0%B8%D0%BB%D0%BB%D0%B5%D1%80&genres.name=%2B%D0%B4%D1%80%D0%B0%D0%BC%D0%B0"
    )
    suspend fun searchWithFilters(
        @Header("X-API-KEY") apiKey: String,
        @FieldMap sortFilters: Map<String, String>,
        @Query(QUERY_PARAM_SORT_TYPE) vararg filters: String
    )

    @GET("person?page=1&limit=10&selectFields=id&selectFields=name&selectFields=enName" +
            "&selectFields=photo&selectFields=sex&selectFields=birthday&selectFields=age" +
            "&selectFields=countAwards&selectFields=profession&sortField=countAwards" +
            "&sortField=movies.general&sortType=-1&sortType=-1")
    suspend fun getActors(
        @Header("X-API-KEY") apiKey: String,
        @Query(QUERY_PARAM_MOVIE_ID) id: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg filters: String
    )

    @GET("movie/search?page={page}&limit={limit}")
    suspend fun searchFilm(
        @Header("X-API-KEY") apiKey: String,
        @Path("page") page: Int = 1,
        @Path("limit") limit: Int = 10,
        @Query(QUERY_PARAM_MOVIE_SEARCH_NAME) movieName: String
    )

    @GET("review?page={page}&limit={limit}&selectFields=id&selectFields=movieId&selectFields=title" +
            "&selectFields=type&selectFields=review&selectFields=date&selectFields=author")
    suspend fun getReviews(
        @Header("X-API-KEY") apiKey: String,
        @Path("page") page: Int = 1,
        @Path("limit") limit: Int = 10,
        @Query(REVIEW_PARAM_MOVIE_ID) movieId: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg filters: String
    )

    @GET("season?page={page}&limit={limit}&selectFields=movieId&selectFields=poster&selectFields=number" +
            "&selectFields=name&selectFields=enName&selectFields=description" +
            "&selectFields=enDescription&selectFields=episodesCount&selectFields=airDate" +
            "&selectFields=episodes&sortField=airDate&sortField=episodes.date&sortType=1&sortType=1&number=1-30")
    suspend fun getSeasons(
        @Header("X-API-KEY") apiKey: String,
        @Path("page") page: Int = 1,
        @Path("limit") limit: Int = 10,
        @Query(QUERY_PARAM_MOVIE_ID) movieId: Int,
        @Query(QUERY_PARAM_SELECT_FIELDS) vararg selectFields: String
    )

    @GET("image?page={page}&limit={limit}&movieId={movieId}&type=cover&type=frame&type=promo&type=still&type=wallpaper")
    suspend fun getPosters(
        @Header("X-API-KEY") apiKey: String,
        @Path("movieId") movieId: Int,
        @Path("page") page: Int = 1,
        @Path("limit") limit: Int = 10,
    )

    companion object {
        //по жанру, по стране производства, по типу контента (сериал/фильм), по году выхода,
        // по рейтингу Кинопоиска (от конкретного значения), поиск по сети производства (HBO,  Netflix  и т.п.)

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

        //https://api.kinopoisk.dev/v1.4/season?page=1&limit=10&selectFields=movieId&selectFields=poster&
        // selectFields=number&selectFields=name&selectFields=enName&selectFields=description&selectFields=enDescription&
        // selectFields=episodesCount&selectFields=airDate&selectFields=episodes&sortField=airDate&sortField=episodes.date&sortType=1
        // &sortType=1&movieId=681831&number=1-30
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

        //    @SerializedName("movieLength") var movieLength: Int? = null,
        //    @SerializedName("isSeries") var isSeries: Boolean? = null,
        //    @SerializedName("totalSeriesLength") var totalSeriesLength: String? = null,
        //    @SerializedName("seriesLength") var seriesLength: String? = null,
        //    @SerializedName("description") var description: String? = null,

        private const val REVIEW_PARAM_REVIEW = "review"
        private const val REVIEW_PARAM_DATE = "date"
        private const val REVIEW_PARAM_TYPE = "type"
        private const val REVIEW_PARAM_AUTHOR = "author"
        private const val REVIEW_PARAM_ID = "id"
        private const val REVIEW_PARAM_MOVIE_ID = "movieId"
        private const val REVIEW_PARAM_TITLE = "title"

        //selectFields=name&selectFields=alternativeName&selectFields=description&selectFields=slogan&selectFields=type&
        // selectFields=year&selectFields=rating&selectFields=ageRating&selectFields=votes&selectFields=seasonsInfo&
        // selectFields=genres&selectFields=countries&selectFields=poster&selectFields=logo&selectFields=networks&
        // selectFields=persons&selectFields=top250&type=tv-series&year=2010-2020&genres.name=%2B%D1%82%D1%80%D0%B8%D0%BB%D0%BB%D0%B5%D1%80&
        // genres.name=%2B%D0%B4%D1%80%D0%B0%D0%BC%D0%B0

        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"

        private const val CURRENCY = "USD"
    }
}