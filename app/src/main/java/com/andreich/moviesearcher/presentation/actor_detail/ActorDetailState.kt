package com.andreich.moviesearcher.presentation.actor_detail

import com.andreich.moviesearcher.domain.model.Actor

data class ActorDetailState(
    val actor: Actor?,
    val isLoading: Boolean
)