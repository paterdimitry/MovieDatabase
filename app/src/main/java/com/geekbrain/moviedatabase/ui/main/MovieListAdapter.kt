package com.geekbrain.moviedatabase.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.model.MovieDTO
import java.net.URL

class MovieListAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var movieList: List<MovieDTO> = listOf()

    fun setMovieList(data: List<MovieDTO>, inContext: Context) {
        context = inContext
        movieList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount() = movieList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: MovieDTO) {
            itemView.apply {
                findViewById<TextView>(R.id.title).text = movie.title
                findViewById<TextView>(R.id.releaseDate).text = movie.release_date
                findViewById<TextView>(R.id.rate).text = movie.vote_average.toString()
                movie.poster_path?.let { getImage(it, findViewById<ImageView>(R.id.poster)) }
                itemView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
            }
        }

        //загрузчик изображений
        private fun getImage(path: String, imageView: ImageView) {
            val baseUrl = "https://image.tmdb.org/t/p/w500/"
            Glide
                .with(context)
                .load(URL(baseUrl + path))
                .into(imageView)
        }
    }
}
