package com.andreich.moviesearcher.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.Entities
import com.andreich.moviesearcher.data.entity.RemoteKeyEntity
import kotlin.reflect.KClass

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("SELECT * FROM remote_key WHERE valueId = :id AND valueType = :type")
    suspend fun getRemoteKeyByMovieID(id: Int, type: KClass<Entities>): RemoteKeyEntity?

    @Query("DELETE FROM remote_key")
    suspend fun clearRemoteKeys()

    @Query("SELECT created_at FROM remote_key WHERE valueType = :type ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(type: KClass<Entities>): Long?
}