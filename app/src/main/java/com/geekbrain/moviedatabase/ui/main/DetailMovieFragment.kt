package com.geekbrain.moviedatabase.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.geekbrain.moviedatabase.databinding.FragmentDetailMovieBinding
import com.geekbrain.moviedatabase.model.*
import java.net.URL

class DetailMovieFragment : Fragment() {

    private val binding: FragmentDetailMovieBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<MovieDTO>(BUNDLE_EXTRA)?.let {
            initView(it)
        }
    }

    private fun initView(movie: MovieDTO) {
        binding.apply {
            detailTitle.text = movie.title
            detailDateRelease.text = movie.release_date
            detailDescription.text = movie.overview
            movie.poster_path?.let { getImage(it, detailPoster) }
            detailRate.text = movie.vote_average.toString()
        }
    }

    private fun getImage(path: String, imageView: ImageView) {
        val baseUrl = "https://image.tmdb.org/t/p/w500/"
        Glide
            .with(context)
            .load(URL(baseUrl + path))
            .into(imageView)
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle) = DetailMovieFragment().also { it.arguments = bundle }
    }
}