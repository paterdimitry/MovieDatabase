package com.geekbrain.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    val id: Int,
    val name: String,
    val job: String,
    val profilePath: String?
): Parcelable
