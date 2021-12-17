package com.geekbrain.moviedatabase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.databinding.FragmentDetailMovieBinding
import com.geekbrain.moviedatabase.databinding.MainFragmentBinding
import com.geekbrain.moviedatabase.model.Movie

class DetailMovieFragment : Fragment() {

    private val binding: FragmentDetailMovieBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let { initView(it) }
    }

    private fun initView(movie: Movie) {
        binding.apply {
            detailTitle.text = movie.title
            detailDateRelease.text = movie.release_date
            detailDescription.text = movie.overview?.let { it ->
                context?.resources?.getText(it)
            }
            movie.posterPath?.let { it -> detailPoster.setImageResource(it) }
            detailRate.text = movie.rate.toString()
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle) = DetailMovieFragment().also { it.arguments = bundle }
    }
}