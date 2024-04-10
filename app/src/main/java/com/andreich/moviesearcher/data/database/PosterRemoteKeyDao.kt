package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.PosterRemoteKeyEntity

@Dao
interface PosterRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PosterRemoteKeyEntity>)

    @Query("SELECT * FROM poster_remote_key WHERE valueId = :id")
    suspend fun getRemoteKeyByValueID(id: String): PosterRemoteKeyEntity?

    @Query("DELETE FROM poster_remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT createdAt FROM poster_remote_key ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}