package com.example.tmdbestud.view.activity

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import com.example.projetoallmoviesfinal.R
import com.example.projetoallmoviesfinal.databinding.ActivityMainBinding
import com.example.tmdbestud.api.response.Movie
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_BACKDROP
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_OVERVIEW
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_POSTER
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_RATING
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_RELEASE_DATE
import com.example.tmdbestud.view.activity.DetailsActivity.Companion.MOVIE_TITLE
import com.example.tmdbestud.view.adapter.listeners.DetailsMovieListener
import com.example.tmdbestud.view.adapter.listeners.MoviesRecycler
import com.example.tmdbestud.view.util.ConnectivityReceiver
import com.example.tmdbestud.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), DetailsMovieListener,
    ConnectivityReceiver.ConnectivityReceiverListener{

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private val popularMoviesRecycler: MoviesRecycler by inject()
    private val topRatedMoviesRecycler: MoviesRecycler by inject()
    private val upcomingMoviesRecycler: MoviesRecycler by inject()


    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        setupRecyclers()
    }


    private fun setupRecyclers() {
        binding.popularMovies.adapter = popularMoviesRecycler
        popularMoviesRecycler.detailsMovieListener = this

        binding.topRatedMovies.adapter = topRatedMoviesRecycler
        topRatedMoviesRecycler.detailsMovieListener = this

        binding. upcomingMovies.adapter = upcomingMoviesRecycler
        upcomingMoviesRecycler.detailsMovieListener = this
    }

    @SuppressLint("ShowToast")
    private fun setupObservers() {
        mainViewModel.initializeLaunch()

        mainViewModel.popularMovie.observe(this, Observer {
            popularMoviesRecycler.setList(it)
            binding.popularMovies.visibility = View.VISIBLE

            binding.sflCharacter.stopShimmer()
            binding.sflCharacter.visibility = View.GONE
        })

        mainViewModel.ratedMovies.observe(this, Observer {
            topRatedMoviesRecycler.setList(it)
            binding.topRatedMovies.visibility = View.VISIBLE

            binding.sflCharacter3.stopShimmer()
            binding.sflCharacter3.visibility = View.GONE
        })

        mainViewModel.upcomingMovies.observe(this, Observer {
            upcomingMoviesRecycler.setList(it)
            binding.upcomingMovies.visibility = View.VISIBLE


            binding.sflCharacter2.stopShimmer()
            binding.sflCharacter2.visibility = View.GONE
        })

        mainViewModel.movieError.observe(this, Observer {
            Toast.makeText(this@MainActivity, "Ops.. Você está Offline", Toast.LENGTH_LONG)
                .show()
        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showMovieDetails(movie: Movie, imageView: ImageView) {
        val intent = Intent(this, MovieDetailsActivity::class.java)

        val elementShared: View = imageView
        elementShared.transitionName = "imagePoster"
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity,
            elementShared, "imagePoster"
        )

        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent, options.toBundle())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun detailsMovieOnClick(movie: Movie, position: Int, imageView: ImageView) {
        showMovieDetails(movie, imageView)
    }


    private fun showConnection(isConnected: Boolean){

        if (isConnected) {
            mSnackBar?.dismiss()
            setupObservers()
        } else {
            val messageToUser = "Você está offline agora!"

            mSnackBar = Snackbar.make(
                findViewById(R.id.layout_constraint),
                messageToUser,
                Snackbar.LENGTH_LONG
            )
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
            Toast.makeText(this@MainActivity, "Você está offline agora!", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showConnection(isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }


}