package com.personal.movieapp.state

import com.personal.movieapp.model.MovieDetail

sealed class MovieDetailUIState {
        object Empty : MovieDetailUIState()
        object Loading : MovieDetailUIState()
        class Loaded(val data: MovieDetail) : MovieDetailUIState()
        class Error(val message: String) : MovieDetailUIState()
    }