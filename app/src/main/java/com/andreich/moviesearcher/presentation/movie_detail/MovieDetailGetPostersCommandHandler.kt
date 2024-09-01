package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.usecase.GetPosterDetailUseCase
import com.andreich.moviesearcher.domain.usecase.GetPostersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailGetPostersCommandHandler @Inject constructor(
    private val getPostersUseCase: GetPosterDetailUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent>{

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadPosters>()
            .flatMapLatest {
                getPostersUseCase.execute(it.movieId).map {
                    MovieDetailEvent.MovieDetailCommandsResultEvent.PostersIsReady(it)
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.localizedMessage ?: "")
            }
    }
}