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
import com.geekbrain.moviedatabase.model.CastDTO
import com.geekbrain.moviedatabase.model.MovieDTO
import java.net.URL

class CastListAdapter(private var onItemViewClickListener: DetailMovieFragment.OnItemViewClickListener?) :
    RecyclerView.Adapter<CastListAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var cast: List<CastDTO> = listOf()

    fun setCastList(data: List<CastDTO>, inContext: Context) {
        context = inContext
        cast = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cast_list_item, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    override fun getItemCount() = cast.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(castDTO: CastDTO) {
            itemView.apply {
                findViewById<TextView>(R.id.name).text = castDTO.name
                findViewById<TextView>(R.id.character).text = castDTO.character
                castDTO.profilePath?.let { getImage(it, findViewById<ImageView>(R.id.actor_image)) }
                itemView.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(castDTO)
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
