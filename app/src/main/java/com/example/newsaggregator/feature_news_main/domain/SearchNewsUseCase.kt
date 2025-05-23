package com.example.newsaggregator.feature_news_main.domain

import com.example.newsaggregator.core.util.RequestResult
import com.example.newsaggregator.feature_news_main.data.repository.NewsRepository
import com.example.newsaggregator.feature_news_main.data.repository.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val repository: NewsRepository) {
    fun execute(query: String?): Flow<RequestResult<List<Article>>> {
        return repository.searchNews(query)
    }
}