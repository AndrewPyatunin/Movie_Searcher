package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity

@Dao
interface MovieSearchHistoryDao {

    @Query("SELECT * FROM history ORDER BY id")
    suspend fun getValues(): List<MovieSearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(request: MovieSearchHistoryEntity)

    @Query("SELECT COUNT(*) FROM history")
    suspend fun getTableSize(): Int

    @Query("SELECT time FROM history ORDER BY time ASC LIMIT 1")
    suspend fun getOldest(): Long

    @Query("DELETE FROM history WHERE time = :time")
    suspend fun deleteByTime(time: Long)

    suspend fun deleteOldest() {
        val oldest = getOldest()
        deleteByTime(oldest)
    }
}