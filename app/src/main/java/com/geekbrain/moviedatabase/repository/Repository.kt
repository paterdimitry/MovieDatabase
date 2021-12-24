package com.geekbrain.moviedatabase.repository

import com.geekbrain.moviedatabase.model.Movie
import com.geekbrain.moviedatabase.model.MovieDTO
import com.geekbrain.moviedatabase.enums.RequestType
import com.geekbrain.moviedatabase.model.CastDTO

interface Repository {
    fun getMovieListFromServer(requestType: RequestType): List<MovieDTO>
    fun getCastFromServer(id: Int): List<CastDTO>
    fun getMovieListMostPopularLocal(): List<Movie>
    fun getMovieListLatestReleasedLocal(): List<Movie>
}