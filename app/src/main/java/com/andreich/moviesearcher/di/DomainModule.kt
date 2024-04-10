package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.data.repo.*
import com.andreich.moviesearcher.domain.repo.*
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @AppScope
    @Binds
    fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository

    @AppScope
    @Binds
    fun bindReviewRepository(impl: ReviewRepositoryImpl): ReviewRepository

    @AppScope
    @Binds
    fun bindPersonRepository(impl: PersonRepositoryImpl): PersonRepository

    @AppScope
    @Binds
    fun bindPosterRepository(impl: PosterRepositoryImpl): PosterRepository

    @AppScope
    @Binds
    fun bindSeasonRepository(impl: SeasonRepositoryImpl): SeasonRepository
}