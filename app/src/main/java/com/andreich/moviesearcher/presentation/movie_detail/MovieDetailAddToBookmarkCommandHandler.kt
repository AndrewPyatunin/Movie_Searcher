package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieBookmarkUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieUseCase
import com.andreich.moviesearcher.domain.usecase.RemoveMovieBookmarkUseCase
import com.andreich.moviesearcher.presentation.runCatchingCancellable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailAddToBookmarkCommandHandler @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
    private val removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.AddToBookmark>()
            .mapLatest {
                runCatchingCancellable {
                    Log.d("MOVIE_BOOKMARK", it.movieId.toString())
                    if (it.isBookmark) {
                        removeMovieBookmarkUseCase.execute(it.movieId)
                    } else
                        insertMovieBookmarkUseCase.execute(getMovieUseCase.execute(it.movieId).first())
                    it
                }.map {
                    MovieDetailEvent.MovieDetailCommandsResultEvent.Success(it.isBookmark)
                }.getOrElse {
                    MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.message.toString())
                }
            }
    }
}