package com.andreich.moviesearcher.di

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.usecase.*
import com.andreich.moviesearcher.presentation.AnalyticsTracker
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailCommandsFlowHandler
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailState
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailStore
import com.andreich.moviesearcher.presentation.actor_detail.ActorDetailUpdate
import com.andreich.moviesearcher.presentation.movie_bookmark.*
import com.andreich.moviesearcher.presentation.movie_detail.*
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
        getMovieUseCase: GetMovieUseCase,
        insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
        getMovieBookmarkUseCase: GetMovieBookmarkUseCase,
        removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase,
        insertMovieUseCase: InsertMovieUseCase
    ): MovieDetailStore {
        return MovieDetailStore(
            initialState = MovieDetailState(
                isBookmark = false,
                isLoading = true,
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
                    getMovieUseCase,
                    getMovieBookmarkUseCase
                ), MovieDetailAddToBookmarkCommandHandler(
                    getMovieUseCase,
                    insertMovieUseCase,
                    insertMovieBookmarkUseCase,
                    removeMovieBookmarkUseCase
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

    @Provides
    fun provideMovieBookmarkStore(
        getBookmarkMoviesUseCase: GetBookmarkMoviesUseCase,
        removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase,
        getMovieUseCase: GetMovieUseCase,
        insertMovieUseCase: InsertMovieUseCase
    ): MovieBookmarkStore {
        return MovieBookmarkStore(
            initialState = MovieBookmarkState(true, emptyList()),
            commandHandlers = listOf(
                MovieBookmarkCommandsFlowHandler(getBookmarkMoviesUseCase),
                MovieBookmarkRemoveCommandHandler(removeMovieBookmarkUseCase, insertMovieUseCase, getMovieUseCase)
            ),
            update = MovieBookmarkUpdate()
        )
    }
}