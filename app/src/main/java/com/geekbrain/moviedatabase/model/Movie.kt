package com.geekbrain.moviedatabase.model

import android.os.Parcelable
import com.geekbrain.moviedatabase.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val rate: Number,
    val posterPath: Int? = null, //TODO временно в Int, переделать в String
    val overview: Int? = null, //TODO временно в Int, переделать в String
    /*val genre: List<Genre>? = null,
    val cast: List<Cast>? = null,
    val crew: List<Crew>? = null*/
): Parcelable

//временная функция заполнения тестового списка фильмов
fun getMovieListMostPopular(): List<Movie> {
    return listOf(
        Movie(1, "Молчание ягнят", "14.02.1991", 8.9, R.drawable.b, R.string.a),
        Movie(2, "Матрица. Перезагрузка", "15.05.2003", 8.2, R.drawable.a, R.string.b),
        Movie(3, "Гарри Поттер и философский камень", "21.03.2002", 8.7, R.drawable.c, R.string.c),
        Movie(4, "Крёстный отец", "15.03.1974", 9.2, R.drawable.d, R.string.d)
    )
}

fun getMovieListLatestRelease(): List<Movie> {
    return listOf(
        Movie(5, "Последний день Земли", "19.05.2021", 7.4, R.drawable.aa, R.string.aa),
        Movie(6, "Отец", "15.04.2021", 8.9, R.drawable.bb, R.string.bb),
        Movie(7, "Чернобыль", "15.04.2021", 7.8, R.drawable.cc, R.string.cc),
        Movie(8, "Мортал комбат", "08.04.2021", 9.2, R.drawable.dd, R.string.dd)
    )
}