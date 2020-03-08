package com.example.themoviedb.ui.movies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.component.EndlessScrollListener
import com.example.themoviedb.databinding.ActivityMovieListBinding
import com.google.android.material.snackbar.Snackbar
import com.example.themoviedb.injection.ViewModelFactory
import com.example.themoviedb.model.database.Status
import kotlinx.android.synthetic.main.activity_movie_list.*
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Rect
import android.view.View
import com.example.themoviedb.ui.detail.MovieActivity
import com.example.themoviedb.viewmodel.MovieListViewModel


class MovieListActivity : AppCompatActivity(), EndlessScrollListener.ScrollToBottomListener, MovieListAdapter.MovieAdapterListener {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var viewModel: MovieListViewModel
    private var errorSnackbar: Snackbar? = null
    private var mMovieListAdapter: MovieListAdapter? = null
    private var mEndlessScrollListener: EndlessScrollListener? = null
    private var mPage = 1
    private var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(this)).get(MovieListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        initView()
        initListener()

        viewModel.movies.observe(this, Observer { it ->

            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    it.data?.let {
                        if (swipeRefreshLayout.isRefreshing) {
                            if (!isLoadMore) {
                                mMovieListAdapter?.clearData()
                            }
                            Handler().postDelayed({ swipeRefreshLayout.isRefreshing = false }, 1000)
                        }
                        mMovieListAdapter?.updateMovieList(it.toMutableList())
                    }
                }
                Status.ERROR -> {
                }
            }

        })

        viewModel.page.observe(this, Observer {
            it?.let {
                mPage = it
                if (mPage == 1) {
                    isLoadMore = false
                }
            }
        })
    }

    private fun initView() {
        // init recycleview
        val linearLayoutManager = GridLayoutManager(this,2)

        movieListRc.layoutManager = linearLayoutManager

        mEndlessScrollListener = EndlessScrollListener(linearLayoutManager, this)
        movieListRc.addOnScrollListener(mEndlessScrollListener!!)

        mMovieListAdapter = MovieListAdapter(this)
        movieListRc.adapter = mMovieListAdapter

        //edit space between item in recycler view
        movieListRc.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view) // item position
                val spanCount = 2
                val spacing = 5//spacing between views in grid

                if (position >= 0) {
                    val column = position % spanCount // item column

                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        })
    }

    private fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            mEndlessScrollListener?.onRefresh()
            viewModel.loadMovies()
        }

    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    override fun onScrollToBottom() {
        swipeRefreshLayout.isRefreshing = true
        isLoadMore = true
        viewModel.loadMovies(mPage + 1)
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onClickItem(movieId: Int, movieTitle: String) {
        val i = Intent(this, MovieActivity::class.java)
        i.putExtra(MovieActivity.MOVIE_ID, movieId)
        i.putExtra(MovieActivity.MOVIE_TITLE, movieTitle)
        startActivity(i)
    }
}