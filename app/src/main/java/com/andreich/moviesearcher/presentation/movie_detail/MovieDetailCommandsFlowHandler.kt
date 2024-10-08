package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailCommandsFlowHandler @Inject constructor(
    private val getPostersUseCase: GetPosterDetailUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getMovieUseCase: GetMovieUseCase,
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadMovie>()
            .flatMapLatest {
                getMovieUseCase.execute(it.movieId).flatMapLatest { movie ->
                    getPersonsUseCase.execute(it.movieId, scope = it.scope)
                        .flatMapLatest { persons ->
                            getReviewsUseCase.execute(it.movieId, scope = it.scope)
                                .flatMapLatest { reviews ->
                                    getPostersUseCase.execute(it.movieId).flowOn(Dispatchers.IO).map { posters ->
                                        Log.d("ACTORS_MOVIE_DETAIL", movie.actors.toString())
                                        Log.d("MOVIE_DETAIL_POSTERS", posters.toString())
                                        MovieDetailEvent.MovieDetailCommandsResultEvent.DataIsReady(
                                            movie = movie,
                                            reviews = reviews,
                                            actors = persons,
                                            posters = posters,
                                            isBookmark = movie.bookmark
                                        )
                                    }
                                }
                        }
                }
                    .catch {
                        MovieDetailEvent.MovieDetailCommandsResultEvent.LoadError(it.message.toString())
                    }
            }
    }
}