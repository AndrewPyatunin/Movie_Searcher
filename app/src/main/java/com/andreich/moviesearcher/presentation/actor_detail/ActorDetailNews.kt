package com.andreich.moviesearcher.presentation.actor_detail

import androidx.fragment.app.Fragment

sealed interface ActorDetailNews {

    class NavigateTo(val fragment: Fragment) : ActorDetailNews

    class ShowError(val message: String) : ActorDetailNews

    object BackPressed : ActorDetailNews
}