package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.RequestEntity

@Dao
interface RequestDao {

    @Query("SELECT COUNT(*) FROM request")
    suspend fun getTableSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: RequestEntity)

    @Query("DELETE FROM request ORDER BY id ASC LIMIT 1")
    fun deleteOldest()
}