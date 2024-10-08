package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListSearchFilmCommandHandler @Inject constructor(private val searchUseCase: SearchFilteredFilmsUseCase) :
    CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.SearchFilm>()
            .flatMapLatest { command ->
                Log.d("COMMAND_HANDLER", "loadData")
                searchUseCase.execute(10, command.requestId, command.name, command.scope).map {
                    Log.d("COMMAND_HANDLER_NAME_SEARCH", it.toString())
                    MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it)
                }
                    .catch {
                        Log.d("COMMAND_HANDLER_ERROR", "throw")
                        MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
                    }
            }
    }
}