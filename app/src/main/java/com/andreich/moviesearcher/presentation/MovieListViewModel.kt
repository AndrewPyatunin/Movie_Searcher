package com.andreich.moviesearcher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andreich.moviesearcher.domain.model.Movie
import com.andreich.moviesearcher.domain.usecase.SearchFilteredFilmsUseCase
import com.andreich.moviesearcher.ui.state.MovieListStateUi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val searchMovieUseCase: SearchFilteredFilmsUseCase
) : ViewModel() {

    private val state = MovieListStateUi("", PagingData.empty(), true)

    private val _movieListState = MutableStateFlow(state)
    val movieListState: StateFlow<MovieListStateUi> = _movieListState

    fun getList(requestId: String) {
        viewModelScope.launch {
            searchMovieUseCase.execute(10, requestId, scope = viewModelScope).collectLatest {
                _movieListState.value = state.copy()
            }
        }

    }

    fun getData(requestId: String): Flow<PagingData<Movie>> {
        return searchMovieUseCase.execute(10, requestId, scope = viewModelScope)
    }

    fun searchFilm(name: String, requestId: String): Flow<PagingData<Movie>> {
        return searchMovieUseCase.execute(10, requestId, name, scope = viewModelScope)
    }
}