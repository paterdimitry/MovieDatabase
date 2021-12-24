package com.geekbrain.moviedatabase.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.geekbrain.moviedatabase.enums.RequestType
import com.geekbrain.moviedatabase.model.*

class RepositoryImpl : Repository {
    //создаем переменную для передачи информации
    private var movieListDTO: MovieListDTO = MovieListDTO(listOf())
    private var castListDTO: CastListDTO = CastListDTO(listOf())
    //список фильмов
    private val onMovieListLoaderListener: MovieListLoader.MovieListLoaderListener =
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
        MovieListLoader(onMovieListLoaderListener).loadData(requestType)

        return movieListDTO.results
    }

    //актерский состав
    private val onCastLoaderListener: CastLoader.CastLoaderListener =
        object : CastLoader.CastLoaderListener{
            override fun onLoaded(castDTO: CastListDTO) {
                TODO("Not yet implemented")
            }

            override fun onFailed(throwable: Throwable) {
                TODO("Not yet implemented")
            }

        }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getCastFromServer(id: Int): List<CastDTO> {
        CastLoader(onCastLoaderListener).loadData(id)
        return castListDTO.cast
    }

    override fun getMovieListMostPopularLocal() = getMovieListMostPopular()
    override fun getMovieListLatestReleasedLocal() = getMovieListLatestRelease()

}