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
        val movie = arguments?.getParcelable<Movie>(BUNDLE_EXTRA)
        initView(movie)
    }

    private fun initView(movie: Movie?) {
        if (movie != null) {
            binding!!.detailTitle.text = movie.title
            binding!!.detailDateRelease.text = movie.release_date
            binding!!.detailDescription.text = context!!.resources.getText(movie.overview!!)
            binding!!.detailPoster.setImageResource(movie.posterPath!!)
            binding!!.detailRate.text = movie.rate.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailMovieFragment {
            val fragment = DetailMovieFragment()
            fragment.arguments = bundle
            return fragment

        }
    }
}