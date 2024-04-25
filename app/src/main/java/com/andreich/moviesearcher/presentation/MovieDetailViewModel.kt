package com.andreich.moviesearcher.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.model.Poster
import com.andreich.moviesearcher.domain.model.Review
import com.andreich.moviesearcher.domain.usecase.GetMovieUseCase
import com.andreich.moviesearcher.domain.usecase.GetPostersUseCase
import com.andreich.moviesearcher.domain.usecase.GetReviewsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getPostersUseCase: GetPostersUseCase
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<Movie>()
    val movieLiveData: LiveData<Movie> get() = _movieLiveData

    fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovieUseCase.execute(id).collectLatest {
                _movieLiveData.postValue(it)
            }
        }
    }

    fun getReviews(movieId: Int): Flow<PagingData<Review>> {
        return getReviewsUseCase.execute(movieId, scope = viewModelScope)
    }

    fun getPosters(movieId: Int): Flow<PagingData<Poster>> {
        return getPostersUseCase.execute(movieId, scope = viewModelScope)
    }
}