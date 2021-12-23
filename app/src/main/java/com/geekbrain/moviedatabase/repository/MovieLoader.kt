package com.geekbrain.moviedatabase.repository

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.geekbrain.moviedatabase.BuildConfig
import com.geekbrain.moviedatabase.model.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class MovieLoader(
    private val listener: MovieDetailLoaderListener
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData(id: Int?) {
        try {
            val baseUrl = "https://api.themoviedb.org/3/movie/"
            val url = when (id) {
                -1 -> "latest"
                else -> "$id"
            }
            val apiKey = "?api_key=${BuildConfig.MOVIE_DATABASE_API_KEY}"
            val languageProperty = "&language=ru-RU"
            val uri = URL(baseUrl + url + apiKey + languageProperty)
            val handler = Handler(Looper.myLooper() ?: Looper.getMainLooper())
            Thread {
                var urlConnection: HttpsURLConnection? = null
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.readTimeout = 10000

                    val bufferReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val result = getLines(bufferReader)

                    val movieDTO = Gson().fromJson(result, MovieDTO::class.java)
                    handler.post { listener.onLoaded(movieDTO) }

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection?.disconnect()
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

    interface MovieDetailLoaderListener {
        fun onLoaded(movieDetail: MovieDTO)
        fun onFailed(throwable: Throwable)
    }
}