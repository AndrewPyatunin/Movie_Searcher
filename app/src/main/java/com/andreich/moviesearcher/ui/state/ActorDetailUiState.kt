package com.andreich.moviesearcher.ui.state

import com.andreich.moviesearcher.ui.ActorDetailItem

data class ActorDetailUiState(
    val actorDetailItem: ActorDetailItem?,
    val progressVisibility: Boolean
)
