package com.andreich.moviesearcher.presentation.movie_list

import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieUseCase
import com.andreich.moviesearcher.domain.usecase.RemoveMovieBookmarkUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListRemoveFromBookmarkCommandHandler @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase
) : CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.RemoveFromBookmark>()
            .flatMapLatest { command ->
                getMovieUseCase.execute(command.movieId).map {
                    insertMovieUseCase.execute(it.copy(bookmark = false)).run {
                        removeMovieBookmarkUseCase.execute(command.movieId).run {
                            MovieListEvent.MovieListCommandsResultEvent.RemovalSuccess(
                                command.movieTitle
                            )
                        }
                    }
                }
            }.catch {
                MovieListEvent.MovieListCommandsResultEvent.RemovalSuccess(it.localizedMessage ?: "")
            }
    }
}