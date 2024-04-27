package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE name LIKE '%' || :requestId || '%' ORDER BY page")
    fun getMovies(requestId: String): PagingSource<Int, MovieEntity>

     @Query(
        "SELECT * FROM movie WHERE requestId = :requestId AND (:genreFilter = '' OR genres LIKE '%' || :genreFilter || '%') " +
                "AND (:countryFilter = '' OR countries LIKE '%' || :countryFilter || '%') " +
                "AND (type = :movieTypeFilter OR :movieTypeFilter = '') " +
                "AND (network = :networkFilter OR :networkFilter = '')" +
                " ORDER BY page"
    )
    fun loadMovies(
        requestId: String,
        genreFilter: String,
        countryFilter: String,
        movieTypeFilter: String,
        networkFilter: String
    ): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovie(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>)
}