package com.andreich.moviesearcher.presentation.movie_bookmark

import com.andreich.moviesearcher.domain.usecase.RemoveMovieBookmarkUseCase
import com.andreich.moviesearcher.presentation.runCatchingCancellable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieBookmarkRemoveCommandHandler @Inject constructor(
    private val removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase
) : CommandsFlowHandler<MovieBookmarkCommand, MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieBookmarkCommand>): Flow<MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {
        return commands.filterIsInstance<MovieBookmarkCommand.RemoveFromBookmark>()
            .mapLatest {
                runCatchingCancellable {
                    removeMovieBookmarkUseCase.execute(it.movieId)
                }.map { nothing ->
                    MovieBookmarkEvent.MovieBookmarkCommandResultEvent.RemoveSuccessful(it.movieTitle)
                }.getOrElse {
                    MovieBookmarkEvent.MovieBookmarkCommandResultEvent.LoadError(it.message.toString())
                }
            }
    }
}