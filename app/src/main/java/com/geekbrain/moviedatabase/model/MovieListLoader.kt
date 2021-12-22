package com.geekbrain.moviedatabase.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.geekbrain.moviedatabase.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

//Загрузчик списка фильмов
class MovieListLoader(
    private val listener: MovieListLoaderListener
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData(requestType: RequestType) {
        try {
            //Формируем ссылку запроса по частям
            val baseUrl = "https://api.themoviedb.org/3/"
            val url = when (requestType) {
                RequestType.POPULAR -> "movie/popular"
                RequestType.TRENDS -> "movie/top_rated"
                RequestType.NOW_PLAYING -> "movie/now_playing"
                RequestType.UPCOMING -> "movie/upcoming"
                else -> return
            }
            val apiKey = "?api_key=${BuildConfig.MOVIE_DATABASE_API_KEY}"
            val property = "&language=ru-RU&region=RU"
            val uri = URL(baseUrl + url + apiKey + property)

            val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())
            Thread {
                //создаем соединение
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000
                    //получаем и интерпретируем данные
                    val bufferReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result = getLines(bufferReader)
                    val movieListDTO = Gson().fromJson(result, MovieListDTO::class.java)
                    handler.post { listener.onLoaded(movieListDTO) }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface MovieListLoaderListener {
        fun onLoaded(movieList: MovieListDTO)
        fun onFailed(throwable: Throwable)
    }
}