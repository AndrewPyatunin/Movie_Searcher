package com.andreich.moviesearcher.di

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.usecase.*
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
        getMovieHistoryUseCase: GetMovieHistoryUseCase,
        getMovieUseCase: GetMovieUseCase,
        insertMovieUseCase: InsertMovieUseCase,
        insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
        removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase
    ): MovieListStore {
        return MovieListStore(
            initialState = MovieListState(true, PagingData.empty()),
            update = MovieListUpdate(),
            commandsHandlers = listOf(
                MovieListCommandsFlowHandler(searchUseCase),
                MovieListSearchFilmCommandHandler(searchUseCase),
                MovieListFilteredSearchCommandHandler(searchUseCase),
                MovieListSortCommandHandler(searchUseCase),
                MovieListShowHistoryCommandHandler(getMovieHistoryUseCase),
                MovieListAddToBookmarkCommandHandler(
                    insertMovieBookmarkUseCase, insertMovieUseCase, getMovieUseCase
                ),
                MovieListRemoveFromBookmarkCommandHandler(
                    getMovieUseCase, insertMovieUseCase, removeMovieBookmarkUseCase
                )
            )
        )
    }

    @Provides
    fun provideMovieDetailStore(
        getPostersUseCase: GetPosterDetailUseCase,
        getPersonsUseCase: GetPersonsUseCase,
        getReviewsUseCase: GetReviewsUseCase,
        getMovieUseCase: GetMovieUseCase,
        insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
        removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase,
        insertMovieUseCase: InsertMovieUseCase,
        getSeasonsUseCase: GetSeasonsUseCase
    ): MovieDetailStore {
        return MovieDetailStore(
            initialState = MovieDetailState(
                isBookmark = false,
                isLoading = true,
                PagingData.empty(),
                emptyList(),
                PagingData.empty(),
                null,
                emptyList()
            ),
            update = MovieDetailUpdate(),
            commandHandlers = listOf(
                MovieDetailCommandsFlowHandler(
                    getPostersUseCase,
                    getPersonsUseCase,
                    getReviewsUseCase,
                    getMovieUseCase
                ), MovieDetailAddToBookmarkCommandHandler(
                    getMovieUseCase,
                    insertMovieUseCase,
                    insertMovieBookmarkUseCase,
                    removeMovieBookmarkUseCase
                ), MovieDetailGetSeasonsCommandHandler(
                    getSeasonsUseCase
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
                MovieBookmarkRemoveCommandHandler(
                    removeMovieBookmarkUseCase,
                    insertMovieUseCase,
                    getMovieUseCase
                )
            ),
            update = MovieBookmarkUpdate()
        )
    }
}