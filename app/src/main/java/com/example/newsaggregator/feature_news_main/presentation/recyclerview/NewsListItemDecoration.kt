package com.example.newsaggregator.feature_news_main.presentation.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class NewsListItemDecoration(
    private val leftOffset: Int = 0,
    private val rightOffset: Int = 0,
    private val topOffset: Int = 0,
    private val bottomOffset: Int = 0,
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }
}