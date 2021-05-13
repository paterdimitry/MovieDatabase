package com.geekbrain.moviedatabase.model

data class Cast(
    val id: Int,
    val name: String,
    val gender: Int,
    val character: String,
    val profilePath: String?
)
