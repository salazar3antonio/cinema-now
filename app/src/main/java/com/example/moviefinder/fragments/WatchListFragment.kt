package com.example.moviefinder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviefinder.R
import com.example.moviefinder.adapters.MovieListAdapter
import com.example.moviefinder.databinding.FragmentWatchListBinding
import com.example.moviefinder.view_models.MovieViewModel

class WatchListFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private lateinit var mWatchListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mWatchListAdapter = MovieListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWatchListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_watch_list, container, false)

        binding.lifecycleOwner = this

        val wantToWatchRecyclerView = binding.rvWatchList
        wantToWatchRecyclerView.layoutManager = GridLayoutManager(context, 3)
        wantToWatchRecyclerView.adapter = mWatchListAdapter

        mMovieViewModel.mAllWantToWatchMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let {
                mWatchListAdapter.mMoviesList = it
            }
        })

        return binding.root
    }
}