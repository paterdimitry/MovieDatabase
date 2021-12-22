package com.geekbrain.moviedatabase

import com.geekbrain.moviedatabase.model.Movie
import com.geekbrain.moviedatabase.model.MovieDTO

sealed class AppState {
    data class Success(val movieList: List<MovieDTO>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading: AppState()
}
