package com.example.newsaggregator.feature_news_main.presentation.model

import com.example.newsaggregator.feature_news_main.data.repository.models.Article

data class NewsState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
)