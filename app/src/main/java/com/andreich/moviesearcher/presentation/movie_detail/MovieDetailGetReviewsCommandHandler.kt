package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.usecase.GetReviewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailGetReviewsCommandHandler @Inject constructor(
    private val getReviewsUseCase: GetReviewsUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent>{

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadReviews>()
            .flatMapLatest {
                getReviewsUseCase.execute(it.movieId, scope = it.scope).map {
                    MovieDetailEvent.MovieDetailCommandsResultEvent.ReviewsIsReady(it)
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.localizedMessage ?: "")
            }
    }
}