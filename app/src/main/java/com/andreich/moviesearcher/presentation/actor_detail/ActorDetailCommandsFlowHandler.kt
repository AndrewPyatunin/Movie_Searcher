package com.andreich.moviesearcher.presentation.actor_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.GetActorUseCase
import com.andreich.moviesearcher.presentation.runCatchingCancellable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class ActorDetailCommandsFlowHandler @Inject constructor(
    private val getActorUseCase: GetActorUseCase
) : CommandsFlowHandler<ActorDetailCommand, ActorDetailEvent.ActorDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<ActorDetailCommand>): Flow<ActorDetailEvent.ActorDetailCommandsResultEvent> {
        return commands.filterIsInstance<ActorDetailCommand.GetActor>()
            .mapLatest {
                runCatchingCancellable {
                    Log.d("PERSON_HANDLER", it.actorId.toString())
                    getActorUseCase.execute(it.actorId)
                }.map {
                    ActorDetailEvent.ActorDetailCommandsResultEvent.DataIsReady(it)
                }.getOrElse {
                    ActorDetailEvent.ActorDetailCommandsResultEvent.LoadError(it.message.orEmpty())
                }
            }
    }
}