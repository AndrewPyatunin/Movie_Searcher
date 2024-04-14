package com.andreich.moviesearcher.presentation

import ru.tinkoff.kotea.core.CommandsFlowHandler
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store
import ru.tinkoff.kotea.logging.KoteaLoggingStore
import javax.inject.Inject

class MovieListStore @Inject constructor(
    private val initialState: MovieListState,
    private val update: MovieListUpdate,
    private val commandsHandlers: List<CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent>>
) : Store<MovieListState, MovieListEvent, MovieListNews> by KoteaLoggingStore(
    initialState = initialState,
    commandsFlowHandlers = commandsHandlers,
    update = update
)