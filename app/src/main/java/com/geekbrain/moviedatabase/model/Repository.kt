package com.geekbrain.moviedatabase.model

interface Repository {
    fun getMovieListFromServer(): List<Movie>
    fun getMovieListLocal(): List<Movie>
}