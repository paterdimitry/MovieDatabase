package com.geekbrain.moviedatabase.model

interface Repository {
    fun getMovieListFromServer(): List<Movie>
    fun getMovieListMostPopularLocal(): List<Movie>
    fun getMovieListLatestReleasedLocal(): List<Movie>
}