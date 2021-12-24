package com.geekbrain.moviedatabase.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CastDTO(
    val id: Int,
    val name: String,
    val gender: Int,
    val character: String,
    @SerializedName("profile_path")
    val profilePath: String?
):Parcelable

data class CastListDTO(
    var cast: List<CastDTO>
)