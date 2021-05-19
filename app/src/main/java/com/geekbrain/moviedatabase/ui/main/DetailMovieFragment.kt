package com.geekbrain.moviedatabase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.databinding.FragmentDetailMovieBinding
import com.geekbrain.moviedatabase.databinding.MainFragmentBinding
import com.geekbrain.moviedatabase.model.Movie

class DetailMovieFragment : Fragment() {

    private var binding: FragmentDetailMovieBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { initView(it) }
    }

    private fun initView(movie: Movie) {
        binding?.apply {
            detailTitle.text = movie.title
            detailDateRelease.text = movie.release_date
            detailDescription.text = movie.overview?.let { it ->
                context?.resources?.getText(it)
            }
            movie.posterPath?.let { it -> detailPoster.setImageResource(it) }
            detailRate.text = movie.rate.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle) = DetailMovieFragment().also { it.arguments = bundle }
    }
}