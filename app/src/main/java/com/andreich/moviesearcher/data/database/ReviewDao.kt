package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.ReviewEntity

@Dao
interface ReviewDao: MyDao<ReviewEntity> {

    @Query("SELECT * FROM review ORDER BY page")
    fun getReviews(): PagingSource<Int, ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(list: List<ReviewEntity>)
}