package com.andreich.moviesearcher.presentation.movie_detail

import androidx.paging.map
import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.GetPersonsUseCase
import com.andreich.moviesearcher.domain.usecase.GetPostersUseCase
import com.andreich.moviesearcher.domain.usecase.GetReviewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class MovieDetailCommandsFlowHandler @Inject constructor(
    private val getPostersUseCase: GetPostersUseCase,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getMovieUseCase: GetMovieUseCase
) : CommandsFlowHandler<MovieDetailCommand, MovieDetailEvent.MovieDetailCommandsResultEvent> {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun handle(commands: Flow<MovieDetailCommand>): Flow<MovieDetailEvent.MovieDetailCommandsResultEvent> {
        return commands.filterIsInstance<MovieDetailCommand.LoadMovie>()
            .flatMapLatest {
                getMovieUseCase.execute(it.movieId).flatMapLatest { movie ->
                    getPersonsUseCase.execute(it.movieId, scope = it.scope).flatMapLatest { persons ->
                        getReviewsUseCase.execute(it.movieId, scope = it.scope).flatMapLatest { reviews ->
                            getPostersUseCase.execute(it.movieId, scope = it.scope).map { posters ->
                                MovieDetailEvent.MovieDetailCommandsResultEvent.DataIsReady(
                                    movie = movie,
                                    actors = persons,
                                    reviews = reviews,
                                    posters = posters
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