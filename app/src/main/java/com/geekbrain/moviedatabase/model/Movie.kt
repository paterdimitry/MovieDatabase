package com.geekbrain.moviedatabase.model

data class Movie(
    val id: Int,
    val title: String,
    val release_date: String,
    val rate: Number,
    val posterPath: Int? = null,
    val overview: String? = null,
    val genre: List<Genre>? = null,
    val cast: List<Cast>? = null,
    val crew: List<Crew>? = null
)
