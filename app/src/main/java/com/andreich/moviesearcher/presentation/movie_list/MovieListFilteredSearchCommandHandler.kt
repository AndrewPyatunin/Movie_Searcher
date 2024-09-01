package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListFilteredSearchCommandHandler @Inject constructor(private val searchUseCase: SearchFilteredFilmsUseCase) :
    CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.SearchFiltered>()
            .flatMapLatest { command ->
                searchUseCase.execute(
                    pageSize = 10,
                    requestId = "",
                    scope = command.scope,
                    filters = command.filters
                ).map {
                    Log.d("COMMAND_HANDLER_FILTERED", command.filters.size.toString())
                    MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it)
                }
                    .catch {
                        MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
                    }
            }
    }
}