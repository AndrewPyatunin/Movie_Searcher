package com.andreich.moviesearcher.di

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.usecase.*
import com.andreich.moviesearcher.presentation.AnalyticsTracker
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailCommandsFlowHandler
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailState
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailStore
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailUpdate
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailCommandsFlowHandler
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailState
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailStore
import com.andreich.moviesearcher.presentation.movie_detail.MovieDetailUpdate
import com.andreich.moviesearcher.presentation.movie_list.*
import dagger.Module
import dagger.Provides

@Module
class StoreModule {

    @Provides
    fun provideMovieListStore(
        searchUseCase: SearchFilteredFilmsUseCase,
        getMovieHistoryUseCase: GetMovieHistoryUseCase
    ): MovieListStore {
        return MovieListStore(
            initialState = MovieListState(true, PagingData.empty()),
            update = MovieListUpdate(AnalyticsTracker()),
            commandsHandlers = listOf(
                MovieListCommandsFlowHandler(searchUseCase),
                MovieListSearchFilmCommandHandler(searchUseCase),
                MovieListFilteredSearchCommandHandler(searchUseCase),
                MovieListSortCommandHandler(searchUseCase),
                MovieListShowHistoryCommandHandler(getMovieHistoryUseCase)
            )
        )
    }

    @Provides
    fun provideMovieDetailStore(
        getPostersUseCase: GetPostersUseCase,
        getPersonsUseCase: GetPersonsUseCase,
        getReviewsUseCase: GetReviewsUseCase,
        getMovieUseCase: GetMovieUseCase
    ): MovieDetailStore {
        return MovieDetailStore(
            initialState = MovieDetailState(
                true,
                PagingData.empty(),
                PagingData.empty(),
                PagingData.empty(),
                null
            ),
            update = MovieDetailUpdate(),
            commandHandlers = listOf(
                MovieDetailCommandsFlowHandler(
                    getPostersUseCase,
                    getPersonsUseCase,
                    getReviewsUseCase,
                    getMovieUseCase
                )
            )
        )
    }

    @Provides
    fun provideActorDetailStore(
        getActorUseCase: GetActorUseCase
    ): ActorDetailStore {
        return ActorDetailStore(
            actorState = ActorDetailState(null, true),
            commandsFlowHandler = listOf(ActorDetailCommandsFlowHandler(getActorUseCase)),
            update = ActorDetailUpdate()
        )
    }
}