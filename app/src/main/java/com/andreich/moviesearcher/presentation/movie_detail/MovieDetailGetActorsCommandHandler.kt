package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.usecase.GetPersonsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailGetActorsCommandHandler @Inject constructor(
    private val getPersonsUseCase: GetPersonsUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent>{

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadPersons>()
            .flatMapLatest {
                getPersonsUseCase.execute(it.movieId, scope = it.scope).map {
                    MovieDetailEvent.MovieDetailCommandsResultEvent.ActorsIsReady(it)
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.localizedMessage ?: "")
            }
    }
}