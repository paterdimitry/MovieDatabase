package com.geekbrain.moviedatabase.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.geekbrain.moviedatabase.AppState
import com.geekbrain.moviedatabase.R
import com.geekbrain.moviedatabase.databinding.MainFragmentBinding
import com.geekbrain.moviedatabase.model.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val binding: MainFragmentBinding by viewBinding(createMethod = CreateMethod.INFLATE)

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            initSpinner()
        }
    }

    //инициализатор Spinner. При выборе категории списка запрашиваем у ViewModel данные для отображения
    private fun initSpinner() {
        val spinner = binding.spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                main.showSnackBar(position.toString())
                viewModel.getMovieList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                main.showSnackBar(getString(R.string.nothing))
            }
        }
    }

    private fun showDetailFragment(movie: MovieDTO) {
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
                initMovieList(appState.movieList)
            }
            is AppState.Loading -> main.showSnackBar(getString(R.string.loading))
            is AppState.Error -> {
                main.showSnackBar(getString(R.string.Error))
                    .setAction(getString(R.string.reload)) { viewModel.getMovieList(0) }
            }
        }
    }

    private fun View.showSnackBar(text: String) =
        Snackbar.make(this, text, Snackbar.LENGTH_LONG).also { it.show() }

    override fun onDestroy() {
        super.onDestroy()
        binding.apply {
            movieList.adapter = null
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: MovieDTO)
    }

    private fun initMovieList(movieList: List<MovieDTO>) {
//добавляем разделители
        val itemDecoration =
            HorizontalItemDecoration(resources.getDimensionPixelOffset(R.dimen.list_space_width))

        binding.movieList.apply {
            addItemDecoration(itemDecoration)
            adapter = MovieListAdapter(object : OnItemViewClickListener {
                override fun onItemViewClick(movie: MovieDTO) = showDetailFragment(movie)
            }).also {
                it.setMovieList(movieList, context)
            }
        }
    }
}