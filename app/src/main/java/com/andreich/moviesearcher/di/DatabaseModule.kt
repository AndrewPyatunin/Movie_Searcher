package com.andreich.moviesearcher.di

import android.content.Context
import com.andreich.moviesearcher.data.database.*
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideDatabase(context: Context): MovieDatabase {
        return MovieDatabase.getInstance(context)
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    fun providePersonDao(database: MovieDatabase): PersonDao {
        return database.personDao()
    }

    @Provides
    fun provideReviewDao(database: MovieDatabase): ReviewDao {
        return database.reviewDao()
    }

    @Provides
    fun provideHistoryDao(database: MovieDatabase): MovieSearchHistoryDao {
        return database.historyDao()
    }

    @Provides
    fun providePosterDao(database: MovieDatabase): PosterDao {
        return database.posterDao()
    }

    @Provides
    fun provideSeasonDao(database: MovieDatabase): SeasonDao {
        return database.seasonDao()
    }

    @Provides
    fun providePersonRemoteKeyDao(database: MovieDatabase): PersonRemoteKeyDao {
        return database.personRemoteKeyDao()
    }

    @Provides
    fun provideMovieRemoteKeyDao(database: MovieDatabase): MovieRemoteKeyDao {
        return database.movieRemoteKeyDao()
    }

    @Provides
    fun provideReviewRemoteKeyDao(database: MovieDatabase): ReviewRemoteKeyDao {
        return database.reviewRemoteKeyDao()
    }

    @Provides
    fun providePosterRemoteKeyDao(database: MovieDatabase): PosterRemoteKeyDao {
        return database.posterRemoteKeyDao()
    }
}