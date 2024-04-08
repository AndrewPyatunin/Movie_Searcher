package com.andreich.moviesearcher.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andreich.moviesearcher.data.entity.PersonEntity

@Dao
interface PersonDao: MyDao<PersonEntity> {

    @Query("SELECT * FROM actor ORDER BY page")
    override fun getValues(): PagingSource<Int, PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(list: List<PersonEntity>)
}
