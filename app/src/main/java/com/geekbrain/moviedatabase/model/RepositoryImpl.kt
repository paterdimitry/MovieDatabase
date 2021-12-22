package com.geekbrain.moviedatabase.model

import android.os.Build
import androidx.annotation.RequiresApi

class RepositoryImpl : Repository {
    //создаем переменную для передачи информации
    private var movieListDTO: MovieListDTO = MovieListDTO(listOf())

    private val onListLoaderListener: MovieListLoader.MovieListLoaderListener =
        object : MovieListLoader.MovieListLoaderListener {
            override fun onLoaded(movieList: MovieListDTO) {
                movieListDTO = movieList
            }

            override fun onFailed(throwable: Throwable) {
                TODO("Not yet implemented")
            }
        }

    //Запрашиваем список фильмов
    @RequiresApi(Build.VERSION_CODES.N)
    override fun getMovieListFromServer(requestType: RequestType): List<MovieDTO> {
        MovieListLoader(onListLoaderListener).loadData(requestType)

        return movieListDTO.results
    }

    override fun getMovieListMostPopularLocal() = getMovieListMostPopular()
    override fun getMovieListLatestReleasedLocal() = getMovieListLatestRelease()

}