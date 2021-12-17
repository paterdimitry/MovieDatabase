package com.geekbrain.moviedatabase.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    val id: Int,
    val name: String,
    val gender: Int,
    val character: String,
    val profilePath: String?
):Parcelable
