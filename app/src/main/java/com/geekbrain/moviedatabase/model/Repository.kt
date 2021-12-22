package com.geekbrain.moviedatabase.model

interface Repository {
    fun getMovieListFromServer(requestType: RequestType): List<MovieDTO>
    fun getMovieListMostPopularLocal(): List<Movie>
    fun getMovieListLatestReleasedLocal(): List<Movie>
}