package com.example.themoviedb.ui.detail

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ActivityMovieBinding
import com.example.themoviedb.injection.ViewModelFactory
import com.example.themoviedb.viewmodel.MovieViewModel
import com.example.themoviedb.utils.loadImageURL
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private var errorSnackbar: Snackbar? = null
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBindingData()

        val extras = intent.extras
        if (extras != null) {
            val movieId = extras.getInt(MOVIE_ID)
            val movieTitle = extras.getString(MOVIE_TITLE)
            viewModel.loadMovieById(movieId)
            title = movieTitle
        }

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        viewModel.movieGenres.observe(this, Observer { it ->
            genresTextView.text = it
        })

        viewModel.movieDuration.observe(this, Observer { it ->
            durationTextView.text = resources.getQuantityString(R.plurals.duration, it, it)
        })
        viewModel.movieOverview.observe(this, Observer { it ->
            overviewTextView.text  = it
        })
        viewModel.getPoster().observe(this, Observer { it ->
            loadImageURL(imageView, it)
        })
    }

    private fun initBindingData() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(MovieViewModel::class.java)
        binding.viewModel = viewModel

    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    companion object {
        const val MOVIE_ID = "movie_id"
        const val MOVIE_TITLE = "movie_title"
    }
}