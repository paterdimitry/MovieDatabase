package com.geekbrain.moviedatabase

import com.geekbrain.moviedatabase.model.Movie

sealed class AppState {
    data class Success(val movieListPopular: List<Movie>, val movieListLatest: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
