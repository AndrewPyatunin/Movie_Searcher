package com.andreich.moviesearcher.presentation.movie_list

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListSortCommandHandler @Inject constructor(
    private val searchFilteredFilmsUseCase: SearchFilteredFilmsUseCase
) : CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.SearchSorted>().flatMapLatest {
            Log.d("COMMAND_HANDLER_SORT", it.sortFilters.entries.joinToString(", "))
            searchFilteredFilmsUseCase.execute(
                pageSize = 10,
                requestId = it.sortId,
                scope = it.scope,
                sortFilters = it.sortFilters
            ).map {
                Log.d("COMMAND_HANDLER_SORT", "data")
                MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it)
            }.catch {
                MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
            }
        }
    }
}