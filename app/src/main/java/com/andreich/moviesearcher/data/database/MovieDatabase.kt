package com.andreich.moviesearcher.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andreich.moviesearcher.data.entity.*

@Database(
    entities = [PersonEntity::class, MovieEntity::class, ReviewEntity::class,
        PosterDetailEntity::class, RemoteKeyEntity::class, SeasonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    //Если реализована пагинация:
    //для списка актеров (если их больше 10)
    //для списка сезонов и серий (если есть)
    //для отзывов пользователей

    companion object {

        var instance: MovieDatabase? = null
        val LOCK = Any()
        private const val DB_NAME = "movieDatabase.db"

        fun getInstance(context: Context): MovieDatabase {

            synchronized(LOCK) {
                instance?.let { return it }
                instance = Room.databaseBuilder(context, MovieDatabase::class.java, DB_NAME).build()
                return instance as MovieDatabase
            }
        }
    }
}