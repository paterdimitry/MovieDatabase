package com.geekbrain.moviedatabase.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrain.moviedatabase.AppState
import com.geekbrain.moviedatabase.model.*

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    //функция для передачи списка фильма из репозитория в View
    fun getMovieList(movieListTypeId: Int) {
        val requestType = RequestType.NONE
        var result: List<MovieDTO> = listOf()
        liveDataToObserve.value = AppState.Loading
        //из-за проблем с синхронизацией процессов и задержки при загрузке данных созданы
        // искусственно условия для адекватного отображения
        Thread {
            result = repositoryImpl.getMovieListFromServer(
                requestType.getType(movieListTypeId)
            )
            Thread.sleep(1000)
            liveDataToObserve.postValue(
                AppState.Success(
                    repositoryImpl.getMovieListFromServer(
                        requestType.getType(movieListTypeId)
                    )
                )
            )
        }.start()
    }

    fun getLiveData() = liveDataToObserve
}