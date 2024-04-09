package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.ReviewRemoteKeyEntity

@Dao
interface ReviewRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ReviewRemoteKeyEntity>)

    @Query("SELECT * FROM review_remote_key WHERE valueId = :id")
    suspend fun getRemoteKeyByValueID(id: Int): ReviewRemoteKeyEntity?

    @Query("DELETE FROM review_remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM review_remote_key ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}