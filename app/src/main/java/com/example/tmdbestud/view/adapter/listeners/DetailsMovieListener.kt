package com.example.tmdbestud.view.adapter.listeners

import android.widget.ImageView
import com.example.tmdbestud.api.response.Movie

interface DetailsMovieListener {

    fun detailsMovieOnClick(movie: Movie, position: Int, imageView: ImageView)
}