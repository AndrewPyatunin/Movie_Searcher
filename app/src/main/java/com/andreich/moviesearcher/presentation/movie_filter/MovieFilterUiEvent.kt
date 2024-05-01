package com.andreich.moviesearcher.presentation.movie_filter

sealed interface MovieFilterUiEvent {

    class ApplyFilters(val filters: Map<String, List<String>>) : MovieFilterUiEvent

    class ApplyPositions(val positions: Map<String, Int>) : MovieFilterUiEvent
}