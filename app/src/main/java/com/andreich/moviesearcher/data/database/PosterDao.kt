package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.PosterEntity

@Dao
interface PosterDao {

    @Query("SELECT * FROM poster_detail WHERE movieId = :movieId ORDER BY page")
    fun getPosters(movieId: Int): PagingSource<Int, PosterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosters(list: List<PosterEntity>)
}