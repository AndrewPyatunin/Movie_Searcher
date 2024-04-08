package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.Entities
import com.andreich.moviesearcher.data.entity.MovieRemoteKeyEntity
import kotlin.reflect.KClass

@Dao
interface MovieRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKeyEntity>)

    @Query("SELECT * FROM movie_remote_key WHERE valueId = :id AND valueType = :type")
    suspend fun getRemoteKeyByValueID(id: Int, type: KClass<out Entities>): MovieRemoteKeyEntity?

    @Query("DELETE FROM movie_remote_key WHERE valueType = :type")
    suspend fun clearRemoteKeys(type: KClass<out Entities>)

    @Query("SELECT created_at FROM movie_remote_key WHERE valueType = :type ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(type: KClass<Entities>): Long?
}