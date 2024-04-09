package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieEntity
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieSearchHistoryDao {

    @Query("SELECT * FROM history ORDER BY id")
    fun getValues(): Flow<MovieSearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(list: MovieSearchHistoryEntity)

    @Query("UPDATE history SET movies = :movies WHERE id = :id")
    suspend fun updateMoviesHistory(movies: List<MovieEntity>, id: Long)
}