package com.andreich.moviesearcher.presentation.actor_detail

import ru.tinkoff.kotea.core.CommandsFlowHandler
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store
import javax.inject.Inject

class ActorDetailStore @Inject constructor(
    private val actorState: ActorDetailState,
    private val commandsFlowHandler: List<CommandsFlowHandler<ActorDetailCommand, ActorDetailEvent>>,
    private val update: ActorDetailUpdate
) : Store<ActorDetailState, ActorDetailEvent, ActorDetailNews> by KoteaStore(
    initialState = actorState,
    commandsFlowHandlers = commandsFlowHandler,
    update = update
)