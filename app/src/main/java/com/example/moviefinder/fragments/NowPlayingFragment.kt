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
import com.example.moviefinder.databinding.FragmentNowPlayingBinding
import com.example.moviefinder.view_models.MovieViewModel

class NowPlayingFragment : Fragment() {

    private val mMovieViewModel: MovieViewModel by lazy {
        ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    private lateinit var mNowPlayingAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMovieViewModel.callNowPlayingAPI()
        mNowPlayingAdapter = MovieListAdapter(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding: FragmentNowPlayingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_now_playing, container, false)

        binding.lifecycleOwner = this

        val nowPlayingRecyclerView = binding.rvNowPlayingList
        nowPlayingRecyclerView.layoutManager = GridLayoutManager(context, 3)
        nowPlayingRecyclerView.adapter = mNowPlayingAdapter

        mMovieViewModel.mAllNowPlayingMovies.observe(viewLifecycleOwner, Observer { movies ->
            movies.let {
                mNowPlayingAdapter.mMoviesList = it
            }
        })

        return binding.root


    }

}