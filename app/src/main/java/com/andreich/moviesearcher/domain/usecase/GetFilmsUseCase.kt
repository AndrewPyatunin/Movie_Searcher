package com.andreich.moviesearcher.domain.usecase

import com.andreich.moviesearcher.domain.MovieRepository

class GetFilmsUseCase(
    private val repository: MovieRepository
) {

    fun execute() = repository.getFilms()
}