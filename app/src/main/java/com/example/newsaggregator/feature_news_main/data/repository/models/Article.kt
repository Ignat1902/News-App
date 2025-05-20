package com.example.newsaggregator.feature_news_main.data.repository.models

data class Article(
    val title: String,
    val description: String,
    val imageUrl: String,
    val link: String,
    val publishedDate: String,
    val creator: String,
    val categories: List<String>,
)
