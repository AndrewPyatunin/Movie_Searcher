package com.andreich.moviesearcher.presentation

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieListCommandsFlowHandler @Inject constructor(private val searchUseCase: SearchFilteredFilmsUseCase) :
    CommandsFlowHandler<MovieListCommand, MovieListEvent.MovieListCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieListCommand>): Flow<MovieListEvent.MovieListCommandsResultEvent> {
        return commands.filterIsInstance<MovieListCommand.LoadData>()
            .flatMapLatest { command ->
                Log.d("COMMAND_HANDLER", "loadData")
                searchUseCase.execute(null, 10, "", null, command.scope).map {
                    Log.d("COMMAND_HANDLER_SUCCESS", it.toString())
                    MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it)
                }
                    .catch {
                        Log.d("COMMAND_HANDLER_ERROR", "throw")
                        MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
                    }
            }/*.filterIsInstance<MovieListCommand.SearchFiltered>()
            .flatMapLatest { command ->
                Log.d("COMMAND_HANDLER", "loadData")
                searchUseCase.execute(command.filters, 10, "", null, command.scope).map {
                    Log.d("COMMAND_HANDLER_SUCCESS", it.toString())
                    MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it)
                }
                    .catch {
                        Log.d("COMMAND_HANDLER_ERROR", "throw")
                        MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString())
                    }
            }*/
    }


    /*.filterIsInstance<MovieListCommand.SearchFilm>()
            .mapLatest { command ->
                runCatchingCancellable {
                    Log.d("COMMAND_HANDLER", "result_search_load")
                    searchUseCase.execute(null,10, "", command.name).toList()
                }
                    .map { MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it) }
                    .getOrElse { MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString()) }
            }.filterIsInstance<MovieListCommand.SearchFiltered>()
            .mapLatest { command ->
                runCatchingCancellable {
                    searchUseCase.execute(command.filters, 10, "", null).toList()
                }
                    .map { MovieListEvent.MovieListCommandsResultEvent.DataIsReady(it) }
                    .getOrElse { MovieListEvent.MovieListCommandsResultEvent.LoadError(it.message.toString()) }
            }*/
}