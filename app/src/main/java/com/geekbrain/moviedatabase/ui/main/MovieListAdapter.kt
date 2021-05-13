package com.geekbrain.moviedatabase.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.model.Movie

class MovieListAdapter(val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView? = null
        var releaseDateTextView: TextView? = null
        var rateTextView: TextView? = null
        var posterImageView: ImageView? = null

        init {
            titleTextView = itemView.findViewById(R.id.title)
            releaseDateTextView = itemView.findViewById(R.id.releaseDate)
            rateTextView = itemView.findViewById(R.id.rate)
            posterImageView = itemView.findViewById(R.id.poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView!!.text= movieList[position].title
        holder.releaseDateTextView!!.text = movieList[position].release_date
        holder.rateTextView!!.text = movieList[position].rate.toString()
        holder.posterImageView!!.setImageResource(movieList[position].posterPath!!)
    }

    override fun getItemCount() = movieList.size
}