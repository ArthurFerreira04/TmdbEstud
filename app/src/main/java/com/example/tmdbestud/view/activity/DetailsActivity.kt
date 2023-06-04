package com.example.tmdbestud.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.projetoallmoviesfinal.R
import com.example.projetoallmoviesfinal.databinding.ActivityMovieDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    companion object{
        const val MOVIE_BACKDROP = "extra_movie_backdrop"
        const val MOVIE_POSTER = "extra_movie_poster"
        const val MOVIE_TITLE = "extra_movie_title"
        const val MOVIE_RATING = "extra_movie_rating"
        const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
        const val MOVIE_OVERVIEW = "extra_movie_overview"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        binding.ibComeBack.setOnClickListener { onBackPressed() }

    }

    private fun populateDetails(extras: Bundle) {

        val movieBackdrop: String? = extras.getString(MOVIE_BACKDROP)
        if (movieBackdrop == null) {
            binding.progressBackdrop.visibility = View.GONE
            binding.mtvImageNotFound.visibility  = View.VISIBLE
        } else {
            binding.progressBackdrop.visibility = View.GONE
            binding.movieBackdrop.visibility = View.VISIBLE
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$movieBackdrop")
                .transform(CenterCrop())
                .into(binding.movieBackdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(binding.moviePoster)
        }

        binding.movieTitle.text = extras.getString(MOVIE_TITLE, "")
        binding.movieRating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        binding.movieReleaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        binding.movieOverview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

}