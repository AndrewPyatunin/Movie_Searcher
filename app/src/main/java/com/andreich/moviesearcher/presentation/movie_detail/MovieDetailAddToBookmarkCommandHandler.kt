package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieBookmarkUseCase
import com.andreich.moviesearcher.domain.usecase.InsertMovieUseCase
import com.andreich.moviesearcher.domain.usecase.RemoveMovieBookmarkUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailAddToBookmarkCommandHandler @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val insertMovieBookmarkUseCase: InsertMovieBookmarkUseCase,
    private val removeMovieBookmarkUseCase: RemoveMovieBookmarkUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.AddToBookmark>()
            .flatMapLatest<MovieDetailCommand.AddToBookmark, MovieDetailEvent.MovieDetailCommandsResultEvent> {
                getMovieUseCase.execute(it.movieId).flatMapLatest { movie ->
                    Log.d("MOVIE_DETAIL_BOOKMARK", it.isBookmark.toString())
                    if (movie.bookmark) {
                        Log.d("MOVIE_DETAIL_BOOKMARK", "remove")
                        removeMovieBookmarkUseCase.execute(it.movieId)
                    } else {
                        Log.d("MOVIE_DETAIL_BOOKMARK", "insert")
                        insertMovieBookmarkUseCase.execute(movie.copy(bookmark = true))
                    }
                    insertMovieUseCase.execute(movie.copy(bookmark = !movie.bookmark)).run {
                        getMovieUseCase.execute(it.movieId).map {
                            MovieDetailEvent.MovieDetailCommandsResultEvent.Success(it.bookmark)
                        }
                    }
                }
            }.catch {
                MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.message.toString())
            }
    }
}