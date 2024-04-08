package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieSearchHistoryEntity

@Dao
interface MovieSearchHistoryDao: MyDao<MovieSearchHistoryEntity> {

    @Query("SELECT * FROM history ORDER BY page")
    override fun getValues(): PagingSource<Int, MovieSearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElement(list: MovieSearchHistoryEntity)
}