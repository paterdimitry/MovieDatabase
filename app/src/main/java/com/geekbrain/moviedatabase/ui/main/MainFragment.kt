package com.geekbrain.moviedatabase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.moviedatabase.AppState
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.databinding.MainFragmentBinding
import com.geekbrain.moviedatabase.model.Movie
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {

    private val SPACE_WIDTH = 30 //ширина отступа между элементами списка

    companion object {
        fun newInstance() = MainFragment()
    }

    private var binding: MainFragmentBinding? = null

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
        /*val observer = Observer<Any> {renderData(it)}
        viewModel.getData().observe(viewLifecycleOwner, observer)*/
        viewModel.getMovieListFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieList = appState.movieData
                Snackbar.make(view!!, "Success", Snackbar.LENGTH_LONG).show()
                initMovieList(movieList)
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
        binding = null
    }

    private fun initMovieList(movieList: List<Movie>) {
        val movieListRV: RecyclerView = binding!!.movieList
        //добавляем разделители
        val itemDecoration =
            HorizontalItemDecoration(resources.getDimensionPixelOffset(R.dimen.list_space_width))
        movieListRV.addItemDecoration(itemDecoration)
        movieListRV.adapter = MovieListAdapter(movieList)
    }
}