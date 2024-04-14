package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.ReviewEntity

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review WHERE movieId = :movieId ORDER BY page")
    fun getReviews(movieId: Int): PagingSource<Int, ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(list: List<ReviewEntity>)
}