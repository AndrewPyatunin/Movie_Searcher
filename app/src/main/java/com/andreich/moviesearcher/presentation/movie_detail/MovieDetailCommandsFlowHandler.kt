package com.andreich.moviesearcher.presentation.movie_detail

import android.util.Log
import com.andreich.moviesearcher.domain.usecase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailCommandsFlowHandler @Inject constructor(
    private val getPostersUseCase: GetPostersUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getMovieUseCase: GetMovieUseCase,
    private val getMovieBookmarkUseCase: GetMovieBookmarkUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadMovie>()
            .flatMapLatest {
                getMovieUseCase.execute(it.movieId).flatMapLatest { movie ->
                    getPersonsUseCase.execute(it.movieId, scope = it.scope).flatMapLatest { persons ->
                        getReviewsUseCase.execute(it.movieId, scope = it.scope).flatMapLatest { reviews ->
                            getPostersUseCase.execute(it.movieId, scope = it.scope).flatMapLatest { posters ->
                                getMovieBookmarkUseCase.execute(it.movieId).map { movieBookmark ->
                                    Log.d("ACTORS_MOVIE_DETAIL", movie.actors.toString())
                                    MovieDetailEvent.MovieDetailCommandsResultEvent.DataIsReady(
                                        movie = movie,
                                        reviews = reviews,
                                        actors = persons,
                                        posters = posters,
                                        isBookmark = movieBookmark?.bookmark == false
                                    )
                                }
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