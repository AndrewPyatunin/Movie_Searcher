package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.ReviewEntity
import com.andreich.moviesearcher.data.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonDao {

    @Query("SELECT * FROM season WHERE movieId = :movieId ORDER BY number")
    fun getSeasons(movieId: Int): Flow<List<SeasonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeasons(list: List<SeasonEntity>)
}