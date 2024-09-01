package com.andreich.moviesearcher.presentation.movie_filter

import com.andreich.moviesearcher.data.network.*
import com.andreich.moviesearcher.ui.state.MovieFilterUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper

class MovieFilterUiStateMapper : UiStateMapper<MovieFilterState, MovieFilterUiState> {

    override fun ResourcesProvider.map(state: MovieFilterState): MovieFilterUiState {
        return MovieFilterUiState(
            state.positions[QUERY_NETWORKS] ?: 0,
            state.positions[QUERY_COUNTRY] ?: 0,
            state.positions[QUERY_GENRE] ?: 0,
            state.positions[QUERY_MOVIE_TYPE] ?: 0,
            state.filters[QUERY_YEAR]?.get(0)?.substringBefore("-") ?: "",
            state.filters[QUERY_YEAR_END]?.get(0)?.substringAfter("-") ?: "",
            state.filters[QUERY_RATING]?.get(0)?.substringBefore("-") ?: ""
        )
    }
}