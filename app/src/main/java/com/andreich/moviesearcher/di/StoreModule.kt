package com.andreich.moviesearcher.di

import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import com.andreich.moviesearcher.presentation.*
import dagger.Module
import dagger.Provides

@Module
class StoreModule {
    @AppScope
    @Provides
    fun provideMovieListStore(searchUseCase: SearchFilteredFilmsUseCase): MovieListStore {
        return MovieListStore(
            initialState = MovieListState(true, PagingData.empty()),
            update = MovieListUpdate(AnalyticsTracker()),
            commandsHandlers = listOf(MovieListCommandsFlowHandler(searchUseCase), MovieListSearchFilmCommandHandler(searchUseCase))
        )
    }
}