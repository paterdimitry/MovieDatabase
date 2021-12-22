package com.geekbrain.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDTO(
    val id: Int?,
    val title: String?,
    val vote_average: Double?,
    val release_date: String?,
    val overview: String?,
    val poster_path: String?
) : Parcelable

data class MovieListDTO(
    var results: List<MovieDTO>
)
