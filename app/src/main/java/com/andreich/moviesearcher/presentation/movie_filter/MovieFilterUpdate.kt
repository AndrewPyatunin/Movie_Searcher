package com.andreich.moviesearcher.presentation.movie_filter

import com.andreich.moviesearcher.ui.screen.MovieListFragment
import ru.tinkoff.kotea.core.dsl.DslUpdate
import java.io.Serializable
import javax.inject.Inject

class MovieFilterUpdate @Inject constructor() :
    DslUpdate<MovieFilterState, MovieFilterUiEvent, MovieFilterCommand, MovieFilterNews>() {

    override fun NextBuilder.update(event: MovieFilterUiEvent) {
        when (event) {
            is MovieFilterUiEvent.ApplyFilters -> {
                news(MovieFilterNews.NavigateTo(MovieListFragment.getInstance(event.filters as Serializable)))
            }
            is MovieFilterUiEvent.ApplyPositions -> {
                state { copy(positions = event.positions) }
            }
        }
    }
}