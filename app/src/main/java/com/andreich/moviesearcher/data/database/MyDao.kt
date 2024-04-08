package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

sealed interface MyDao<T: Any> {
    @Query("SELECT * FROM actor ORDER BY page")
    fun getValues(): PagingSource<Int, T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<T>)
}