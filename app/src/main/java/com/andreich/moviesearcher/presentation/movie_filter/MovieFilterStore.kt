package com.andreich.moviesearcher.presentation.movie_filter

import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store

class MovieFilterStore(
    private val update: MovieFilterUpdate,
    private val initialState: MovieFilterState?
) : Store<MovieFilterState, MovieFilterUiEvent, MovieFilterNews> by KoteaStore(
    initialState = initialState ?: MovieFilterState(emptyMap(), emptyMap()),
    update = update
)