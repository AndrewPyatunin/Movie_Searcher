package com.andreich.moviesearcher.presentation.actor_detail

import android.graphics.Color
import android.util.Log
import com.andreich.moviesearcher.domain.model.Actor
import com.andreich.moviesearcher.domain.model.Spouses
import com.andreich.moviesearcher.presentation.HtmlStringFormatter
import com.andreich.moviesearcher.ui.ActorDetailItem
import com.andreich.moviesearcher.ui.state.ActorDetailUiState
import ru.tinkoff.kotea.android.ui.ResourcesProvider
import ru.tinkoff.kotea.android.ui.UiStateMapper

class ActorDetailUiStateMapper(
    private val htmlStringFormatter: HtmlStringFormatter
) : UiStateMapper<ActorDetailState, ActorDetailUiState> {

    override fun ResourcesProvider.map(state: ActorDetailState): ActorDetailUiState {
        return ActorDetailUiState(
            actorDetailItem = state.actor?.mapActorToActorDetailItem(),
            progressVisibility = state.isLoading
        )
    }

    private fun Double.ratingColor(): Int {
        return when {
            this >= 7.0 -> Color.GREEN
            this < 7.0 && this >= 5.0 -> Color.GRAY
            this <= 5.0 && this > 0.0 -> Color.RED
            else -> Color.GRAY
        }
    }

    private fun Spouses.spousesInfo(): String {
        val spouseBuilder = StringBuilder()
        Log.d("ACTORS_SPOUSES", this.name.toString())
        spouseBuilder.append(this.name)
        if (this.divorced == true) {
            spouseBuilder.append(" (развод)")
        }
        return spouseBuilder.toString()
    }

    private fun Int.calculateAge(): String {
        return when {
            this % 10 == 1 -> "$this год"
            this % 10 == 2 || this % 10 == 3 || this % 10 == 4 -> "$this года"
            else -> "$this лет"
        }
    }

    private fun Spouses.spouseChildren(): String {
        return when(this.children) {
            1 -> "один ребенок"
            2 -> "двое детей"
            3 -> "трое детей"
            4 -> "четверо детей"
            5 -> "пятеро детей"
            6 -> "шестеро детей"
            else -> ""
        }
    }

    private fun Actor.mapActorToActorDetailItem(): ActorDetailItem {
        return ActorDetailItem(
            id = this.id,
            name = this.name,
            enName = this.enName,
            photo = this.photo,
            sex = this.sex,
            growth = "${this.growth} см",
            birthday = this.birthday?.substring(0, 10),
            death = this.death?.substring(0, 10),
            age = age?.calculateAge(),
            birthPlace = this.birthPlace.joinToString(", "),
            deathPlace = this.death,
            spouses = this.spouses.joinToString(", ") {
                "${it.spousesInfo()} ${it.spouseChildren()}"
            },
            countAwards = this.countAwards,
            profession = this.profession.joinToString(", "),
            facts = this.facts.joinToString(", "),
            movies = this.movies.map {
                it.copy(ratingColor = it.rating?.ratingColor())
            },
        )
    }
}