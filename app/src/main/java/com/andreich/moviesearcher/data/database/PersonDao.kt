package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.ActorEntity
import com.andreich.moviesearcher.data.entity.PersonEntity

@Dao
interface PersonDao {

    @Query("SELECT * FROM actor where id = :movieId") //WHERE (:movieId = '' OR movieIds LIKE '%' || :movieId || '%')")
    fun getActors(movieId: Int): PagingSource<Int, PersonEntity>

    @Query("SELECT * FROM actor_detail WHERE id = :actorId")
    suspend fun getActor(actorId: Int): ActorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(list: List<PersonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(actor: ActorEntity)
}
