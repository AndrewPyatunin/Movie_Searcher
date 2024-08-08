package com.andreich.moviesearcher.di

import com.andreich.moviesearcher.data.entity.*
import com.andreich.moviesearcher.data.mapper.*
import com.andreich.moviesearcher.domain.model.*
import dagger.Binds
import dagger.Module

@Module
interface EntityMapperModule {

    @Binds
    fun bindPersonEntityMapper(impl: PersonEntityToPersonMapper): EntityToModelMapper<PersonEntity, Person>

    @Binds
    fun bindMovieEntityMapper(impl: MovieEntityToMovieMapper): EntityToModelMapper<MovieEntity, Movie>

    @Binds
    fun bindReviewEntityMapper(impl: ReviewEntityToReviewMapper): EntityToModelMapper<ReviewEntity, Review>

    @Binds
    fun bindPosterEntityMapper(impl: PosterEntityToPosterMapper): EntityToModelMapper<PosterEntity, Poster>

    @Binds
    fun bindSeasonEntityMapper(impl: SeasonEntityToSeasonMapper): EntityToModelMapper<SeasonEntity, Season>

    @Binds
    fun bindActorEntityMapper(impl: ActorEntityToActorMapper): EntityToModelMapper<ActorEntity, Actor>
}