package com.example.themoviedb.component

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
    private var linearLayoutManager: LinearLayoutManager,
    private var listener: ScrollToBottomListener
) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 2
    private var firstVisibleItem: Int = 0
    private var visibleItemCount:Int = 0
    private var totalItemCount:Int = 0


    fun onRefresh() {
        previousTotal = 0
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = linearLayoutManager.itemCount
        firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            this.listener.onScrollToBottom()
            loading = true
        }
    }

     interface ScrollToBottomListener {
        fun onScrollToBottom()
    }
}