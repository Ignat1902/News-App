package com.example.newsaggregator.data.datasource.local.models

import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithCategories(
    @Embedded val news: ArticleDBO,
    @Relation(
        parentColumn = "id",
        entityColumn = "news_id"
    )
    val categories: List<CategoryDBO>
)
