package com.andreich.moviesearcher.presentation.movie_bookmark

import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieUseCase
import com.andreich.moviesearcher.domain.usecase.RemoveMovieBookmarkUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieBookmarkRemoveCommandHandler @Inject constructor(
    private val removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase
) : CommandsFlowHandler<MovieBookmarkCommand, MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieBookmarkCommand>): Flow<MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {
        return commands.filterIsInstance<MovieBookmarkCommand.RemoveFromBookmark>()
            .flatMapLatest<MovieBookmarkCommand.RemoveFromBookmark, MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {
                getMovieUseCase.execute(it.movieId).map { movie ->
                    removeMovieBookmarkUseCase.execute(it.movieId)
                    insertMovieUseCase.execute(movie.copy(bookmark = false)).run {
                        MovieBookmarkEvent.MovieBookmarkCommandResultEvent.RemoveSuccessful(it.movieTitle)
                    }
                }
            }.catch {
                MovieBookmarkEvent.MovieBookmarkCommandResultEvent.LoadError(it.message.toString())
            }
    }
}