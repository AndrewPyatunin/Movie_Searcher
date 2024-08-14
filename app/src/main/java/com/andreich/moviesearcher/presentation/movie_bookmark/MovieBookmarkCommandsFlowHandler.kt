package com.andreich.moviesearcher.presentation.movie_bookmark

import com.andreich.moviesearcher.domain.usecase.GetBookmarkMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieBookmarkCommandsFlowHandler @Inject constructor(
    private val getBookmarkMoviesUseCase: GetBookmarkMoviesUseCase
) : CommandsFlowHandler<MovieBookmarkCommand, MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieBookmarkCommand>): Flow<MovieBookmarkEvent.MovieBookmarkCommandResultEvent> {
        return commands.filterIsInstance<MovieBookmarkCommand.LoadMovies>()
            .flatMapLatest {
                getBookmarkMoviesUseCase.execute().map {
                    MovieBookmarkEvent.MovieBookmarkCommandResultEvent.DataIsReady(it)
                }.catch {
                    MovieBookmarkEvent.MovieBookmarkCommandResultEvent.LoadError(it.message.toString())
                }
            }
    }
}