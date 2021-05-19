package com.geekbrain.moviedatabase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.moviedatabase.AppState
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.databinding.MainFragmentBinding
import com.geekbrain.moviedatabase.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var binding: MainFragmentBinding? = null

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.apply {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getMovieListFromLocalSource()
        }
    }

    private fun showDetailFragment(movie: Movie) {
        activity?.supportFragmentManager?.apply {
            beginTransaction()
                .add(R.id.container, DetailMovieFragment.newInstance(Bundle().apply {
                    putParcelable(DetailMovieFragment.BUNDLE_EXTRA, movie)
                }))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                main.showSnackBar("Списки фильмов")
                initMovieList(appState.movieListPopular, appState.movieListLatest)
            }
            is AppState.Loading -> main.showSnackBar("Loading...")
            is AppState.Error -> {
                main.showSnackBar("Error occured")
                    .setAction("Reload") { viewModel.getMovieListFromLocalSource() }
            }
        }
    }

    private fun View.showSnackBar(text: String) = Snackbar.make(this, text, Snackbar.LENGTH_LONG).also { it.show() }


    override fun onDestroy() {
        super.onDestroy()
        binding?.apply {
            movieListPopular.adapter = null
            movieListLatest.adapter = null
        }
        binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    private fun initMovieList(movieListPopular: List<Movie>, movieListLatest: List<Movie>) {
//добавляем разделители
        val itemDecoration =
            HorizontalItemDecoration(resources.getDimensionPixelOffset(R.dimen.list_space_width))

        binding?.movieListPopular?.apply {
            addItemDecoration(itemDecoration)
            adapter = MovieListAdapter(object : OnItemViewClickListener {
                override fun onItemViewClick(movie: Movie) = showDetailFragment(movie)
            }).also {
                it.setMovieList(movieListPopular)
            }
        }

        binding?.movieListLatest?.apply {
            addItemDecoration(itemDecoration)
            adapter = MovieListAdapter(object : OnItemViewClickListener {
                override fun onItemViewClick(movie: Movie) = showDetailFragment(movie)
            }).also {
                it.setMovieList(movieListLatest)
            }
        }

    }
}