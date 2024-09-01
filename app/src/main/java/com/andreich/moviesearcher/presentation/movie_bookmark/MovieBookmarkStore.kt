package com.andreich.moviesearcher.presentation.movie_bookmark

import ru.tinkoff.kotea.core.CommandsFlowHandler
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store
import javax.inject.Inject

class MovieBookmarkStore @Inject constructor(
    private val initialState: MovieBookmarkState,
    private val commandHandlers: List<CommandsFlowHandler<MovieBookmarkCommand, MovieBookmarkEvent>>,
    private val update: MovieBookmarkUpdate
) : Store<MovieBookmarkState, MovieBookmarkEvent, MovieBookmarkNews> by KoteaStore(
    initialState = initialState,
    commandsFlowHandlers = commandHandlers,
    update = update,
)