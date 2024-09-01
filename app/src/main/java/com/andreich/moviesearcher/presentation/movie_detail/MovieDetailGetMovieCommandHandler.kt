package com.andreich.moviesearcher.presentation.movie_detail

import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailGetMovieCommandHandler @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadMovie>()
            .flatMapLatest {
                getMovieUseCase.execute(it.movieId).map { movie ->
                    MovieDetailEvent.MovieDetailCommandsResultEvent.MovieIsReady(movie, movie.bookmark)
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.localizedMessage ?: "")
            }
    }
}