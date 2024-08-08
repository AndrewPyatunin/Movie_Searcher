package com.andreich.moviesearcher.presentation.movie_filter

import android.util.Log
import com.andreich.moviesearcher.ui.screen.MovieListFragment
import ru.tinkoff.kotea.core.dsl.DslUpdate
import javax.inject.Inject

class MovieFilterUpdate @Inject constructor() :
    DslUpdate<MovieFilterState, MovieFilterUiEvent, MovieFilterCommand, MovieFilterNews>() {

    override fun NextBuilder.update(event: MovieFilterUiEvent) {
        when (event) {
            is MovieFilterUiEvent.ApplyFilters -> {
                Log.d("FILTER_STATE_POS", event.positions.toString())
                news(MovieFilterNews.NavigateTo(MovieListFragment.getInstance(MovieFilterState(event.filters, event.positions))))
            }
            is MovieFilterUiEvent.ApplyPositions -> {
                state { copy(positions = event.positions) }
            }
            MovieFilterUiEvent.ResetFilters -> {
                state { copy(filters = emptyMap(), positions = emptyMap()) }
                news(MovieFilterNews.ResetFilters)
            }
            MovieFilterUiEvent.ApplyFiltersClicked -> {
                news(MovieFilterNews.ApplyFilters)
            }
        }
    }
}