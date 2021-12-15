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

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var binding: MainFragmentBinding? = null
    private val adapterPopularMovieRV = MovieListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            showDetailFragment(movie)
        }
    })
    private val adapterLatestMovieRV = MovieListAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: Movie) {
            showDetailFragment(movie)
        }
    })

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getMovieListFromLocalSource()
    }

    private fun showDetailFragment(movie: Movie) {
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailMovieFragment.BUNDLE_EXTRA, movie)
            manager.beginTransaction()
                .add(R.id.container, DetailMovieFragment.newInstance(bundle))
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieListPopular: List<Movie> = appState.movieListPopular
                val movieListLatest: List<Movie> = appState.movieListLatest
                    Snackbar.make(view!!, "Success", Snackbar.LENGTH_LONG).show()
                initMovieList(movieListPopular, movieListLatest)
            }
            is AppState.Loading -> Snackbar.make(view!!, "Загружаем", Snackbar.LENGTH_LONG).show()
            is AppState.Error -> {
                Snackbar
                    .make(view!!, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovieListFromLocalSource() }
                    .show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adapterLatestMovieRV.removeListener()
        adapterPopularMovieRV.removeListener()
        binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: Movie)
    }

    private fun initMovieList(movieListPopular: List<Movie>, movieListLatest: List<Movie>) {

        val movieListPopularRV: RecyclerView = binding!!.movieListPopular
        val movieListLatestRV: RecyclerView = binding!!.movieListLatest
        //добавляем разделители
        val itemDecoration =
            HorizontalItemDecoration(resources.getDimensionPixelOffset(R.dimen.list_space_width))

        movieListPopularRV.addItemDecoration(itemDecoration)
        movieListLatestRV.addItemDecoration(itemDecoration)

        adapterPopularMovieRV.setMovieList(movieListPopular)
        movieListPopularRV.adapter = adapterPopularMovieRV

        adapterLatestMovieRV.setMovieList(movieListLatest)
        movieListLatestRV.adapter = adapterLatestMovieRV
    }
}