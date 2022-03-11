package com.personal.movieapp.viewmodel

import androidx.lifecycle.*
import com.personal.movieapp.coroutine.CoroutineDispatchers
import com.personal.movieapp.repo.MovieDetailsRepo
import com.personal.movieapp.state.MovieDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val movieDetailsRepo: MovieDetailsRepo,
    private val coroutineDispatchers: CoroutineDispatchers
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUIState>(MovieDetailUIState.Empty)
    val uiState: StateFlow<MovieDetailUIState> = _uiState

    init {
        fetchMovieDetails()
    }

    private fun fetchMovieDetails() {
        _uiState.value = MovieDetailUIState.Loading
        viewModelScope.launch(coroutineDispatchers.io) {

            movieDetailsRepo.fetchMovieDetail()
                .catch {
                    it.message?.apply {
                        MovieDetailUIState.Error(this)
                    } ?: MovieDetailUIState.Error("Network error")
                }
                .collect {
                    val movieDetail = it.getOrNull()
                    movieDetail?.apply {
                        _uiState.value = MovieDetailUIState.Loaded(this)
                    } ?: MovieDetailUIState.Error("Something went wrong")
                }
        }
    }


}