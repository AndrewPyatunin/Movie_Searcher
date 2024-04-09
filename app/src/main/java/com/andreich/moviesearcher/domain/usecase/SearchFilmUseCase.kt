package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.repo.MovieRepository
import javax.inject.Inject

class SearchFilmUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend fun execute(movieName: String) = repository.searchFilm(movieName)
}