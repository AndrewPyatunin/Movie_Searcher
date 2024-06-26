package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.MovieRemoteKeyEntity

@Dao
interface MovieRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKeyEntity>)

    @Query("SELECT * FROM movie_remote_key WHERE valueId = :id")
    suspend fun getRemoteKeyByValueID(id: Int): MovieRemoteKeyEntity?

    @Query("DELETE FROM movie_remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT createdAt FROM movie_remote_key ORDER BY createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}