package com.geekbrain.moviedatabase.model

//Типы запросов списков фильмов.
enum class RequestType {
    POPULAR,        //популярные
    TRENDS,         //в тренде (наибольшая оценка)
    NOW_PLAYING,    //в прокате сейчас
    UPCOMING,       //ждут выхода на экран
    NONE;           //пустой запрос. технический вариант

    //функция соотносит порядковый номер записи со значением. необходима при выборе RequestType
    // исходя из выбранного значения в Spinner
    fun getType(id: Int): RequestType {
        return when (id) {
            0 -> POPULAR
            1 -> TRENDS
            2 -> NOW_PLAYING
            3 -> UPCOMING
            else -> NONE
        }
    }
}