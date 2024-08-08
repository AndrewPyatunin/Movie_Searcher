package com.andreich.moviesearcher.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andreich.moviesearcher.data.entity.*

@Database(
    entities = [PersonEntity::class, MovieEntity::class, ReviewEntity::class,
        PosterEntity::class, SeasonEntity::class, ActorEntity::class, MovieSearchHistoryEntity::class,
        PersonRemoteKeyEntity::class, ReviewRemoteKeyEntity::class, PosterRemoteKeyEntity::class,
        MovieRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [Converter::class])
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

    abstract fun movieDao(): MovieDao

    abstract fun personDao(): PersonDao

    abstract fun reviewDao(): ReviewDao

    abstract fun seasonDao(): SeasonDao

    abstract fun posterDao(): PosterDao

    abstract fun historyDao(): MovieSearchHistoryDao

    abstract fun movieRemoteKeyDao(): MovieRemoteKeyDao

    abstract fun reviewRemoteKeyDao(): ReviewRemoteKeyDao

    abstract fun personRemoteKeyDao(): PersonRemoteKeyDao

    abstract fun posterRemoteKeyDao(): PosterRemoteKeyDao
}