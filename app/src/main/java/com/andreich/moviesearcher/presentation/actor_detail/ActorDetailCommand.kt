package com.andreich.moviesearcher.presentation.actor_detail

import kotlinx.coroutines.CoroutineScope

sealed interface ActorDetailCommand {

    class GetActor(val scope: CoroutineScope, val actorId: Int) : ActorDetailCommand
}