package com.andreich.moviesearcher.presentation.movie_list

import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieBookmarkUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListAddToBookmarkCommandHandler @Inject constructor(
    private val insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val getMovieUseCase: GetMovieUseCase
) : CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.AddToBookmark>()
            .flatMapLatest { command ->
                getMovieUseCase.execute(command.movieId).map { movie ->
                    insertMovieBookmarkUseCase.execute(movie.copy(bookmark = true)).run {
                        insertMovieUseCase.execute(movie.copy(bookmark = true)).run {
                            MovieListEvent.MovieListCommandsResultEvent.AdditionSuccess(
                                command.movieTitle
                            )
                        }
                    }
                }
            }.catch {
                MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
            }
    }
}