package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.GetMovieHistoryUseCase
import com.andreich.moviesearcher.presentation.runCatchingCancellable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListShowHistoryCommandHandler @Inject constructor(
    private val getMovieHistoryUseCase: GetMovieHistoryUseCase
) : CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.GetHistory>()
            .mapLatest {
                runCatchingCancellable {
                    getMovieHistoryUseCase.execute()
                }
                    .map {
                        Log.d("COMMAND_HANDLER_HISTORY", it.size.toString())
                        MovieListEvent.MovieListCommandsResultEvent.SearchHistoryIsReady(it)
                    }
                    .getOrElse {
                        MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
                    }
            }
    }
}