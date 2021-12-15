package com.geekbrain.moviedatabase.model

import com.geekbrain.moviedatabase.R

class RepositoryImpl : Repository {
    override fun getMovieListFromServer() = initLocalMovieList()


    override fun getMovieListLocal() = initLocalMovieList()

    //временная функция заполнения тестового списка фильмов
    private fun initLocalMovieList(): List<Movie> {
        val movieListLocal: List<Movie>
        val movie1 = Movie(1, "Молчание ягнят", "14.02.1991", 8.9, R.drawable.b)
        val movie2 = Movie(2, "Матрица. Перезагрузка людей и животных по всему миру", "15.05.2003", 8.2, R.drawable.a)
        movieListLocal = listOf(movie1, movie2)
        return movieListLocal
    }
}