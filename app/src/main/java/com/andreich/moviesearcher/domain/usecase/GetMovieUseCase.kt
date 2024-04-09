package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.MovieRepository
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    fun execute(movieId: Int) = repository.getMovie(movieId)
}