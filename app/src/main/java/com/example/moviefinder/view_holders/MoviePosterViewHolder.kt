package com.example.moviefinder.view_holders

import android.view.View
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviefinder.R
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.fragments.NowPlayingFragmentDirections
import com.example.moviefinder.fragments.WatchListFragmentDirections
import com.example.moviefinder.utils.buildMoviePosterUri

class MoviePosterViewHolder(
    itemView: View,
    val mPosterImageView: ImageView = itemView.findViewById(R.id.iv_movie_poster)
) : RecyclerView.ViewHolder(itemView) {

    fun bindMoviePoster(movie: Movie) {

        val posterUri = buildMoviePosterUri(movie.poster)

        Glide.with(itemView).load(posterUri).into(mPosterImageView)

        itemView.setOnClickListener {

            val navController = it.findNavController()
            val currentDestination = it.findNavController().currentDestination?.id
            val nowPlayingAction = NowPlayingFragmentDirections.actionNowPlayingFragmentToMovieDetailsFragment(movie)
            val watchListAction = WatchListFragmentDirections.actionWatchListToMovieDetailsFragment(movie)

            if (currentDestination?.equals(R.id.nowPlaying) == true) {
                navController.navigate(nowPlayingAction)
            } else {
                navController.navigate(watchListAction)
            }

        }

    }


}