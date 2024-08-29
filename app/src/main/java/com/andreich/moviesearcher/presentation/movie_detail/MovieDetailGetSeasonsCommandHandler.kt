package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.GetSeasonsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailGetSeasonsCommandHandler @Inject constructor(
    private val getSeasonsUseCase: GetSeasonsUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadSeasons>()
            .flatMapLatest {
                getSeasonsUseCase.execute(it.movieId).map {
                    Log.d("MOVIE_DETAIL_SEASON", it.joinToString())
                    MovieDetailEvent.MovieDetailCommandsResultEvent.SeasonIsReady(it)
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.localizedMessage ?: "")
            }
    }


}