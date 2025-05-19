package com.example.newsaggregator.data.repository.models

data class Article(
    val title: String,
    val description: String,
    val image: String,
    val link: String,
    val publishedDate: String,
    val creator: String,
    val categories: List<String>,
)
