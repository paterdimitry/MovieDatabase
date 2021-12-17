package com.geekbrain.moviedatabase.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.model.Movie

class MovieListAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var movieList: List<Movie> = listOf()

    fun setMovieList(data: List<Movie>) {
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

        fun bind(movie: Movie) {
            itemView.apply {
                findViewById<TextView>(R.id.title).text = movie.title
                findViewById<TextView>(R.id.releaseDate).text = movie.release_date
                findViewById<TextView>(R.id.rate).text = movie.rate.toString()
                movie.posterPath?.let { findViewById<ImageView>(R.id.poster).setImageResource(it) }
                itemView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(movie)
                }
            }
        }
    }
}
