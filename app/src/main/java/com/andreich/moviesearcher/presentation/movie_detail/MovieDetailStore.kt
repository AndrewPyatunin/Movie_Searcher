package com.andreich.moviesearcher.presentation.movie_detail

import ru.tinkoff.kotea.core.CommandsFlowHandler
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store
import javax.inject.Inject

class MovieDetailStore @Inject constructor(
    private val initialState: MovieDetailState,
    private val update: MovieDetailUpdate,
    private val commandHandlers: List<CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent>>
) : Store<MovieDetailState, MovieDetailEvent, MovieDetailNews> by KoteaStore(
    initialState = initialState,
    commandsFlowHandlers = commandHandlers,
    update = update,
)