package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao: MyDao<MovieEntity> {

    @Query("SELECT * FROM movie WHERE requestId = :requestId ORDER BY page")
    fun getMovies(requestId: Long): PagingSource<Int, MovieEntity>

    @Query("SELECT FROM movie WHERE movieId = :id")
    fun getMovie(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(list: List<MovieEntity>)
}