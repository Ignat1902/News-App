package com.example.newsaggregator.feature_news_main.data.repository

import com.example.newsaggregator.feature_news_main.data.datasource.local.models.ArticleDBO
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.ArticleWithCategories
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.CategoryDBO
import com.example.newsaggregator.feature_news_main.data.datasource.remote.models.ArticleDto
import com.example.newsaggregator.feature_news_main.data.datasource.remote.models.CategoryDto
import com.example.newsaggregator.feature_news_main.data.repository.models.Article


fun ArticleDto.toArticleDbo(): ArticleDBO {
    return ArticleDBO(
        id = 0,
        title = this.title,
        link = this.link,
        description = this.description,
        pubDate = this.pubDate,
        creator = this.dcCreator,
        imageUrl = this.contents[1].url
    )
}

fun CategoryDto.toCategoryDbo(newsId: Long): CategoryDBO {
    return CategoryDBO(
        categoryId = 0,
        name = this.value,
        newsId = newsId
    )
}

fun ArticleWithCategories.toArticle(): Article {
    return Article(
        title = this.news.title,
        description = this.news.description,
        imageUrl = this.news.imageUrl,
        link = this.news.link,
        publishedDate = this.news.pubDate,
        creator = this.news.creator,
        categories = this.categories.map { it.name },
    )
}
