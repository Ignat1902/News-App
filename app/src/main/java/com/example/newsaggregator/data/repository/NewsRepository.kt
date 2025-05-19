package com.example.newsaggregator.data.repository

import com.example.newsaggregator.data.datasource.RequestResult
import com.example.newsaggregator.data.datasource.local.NewsDatabase
import com.example.newsaggregator.data.datasource.remote.RssFeedApi
import com.example.newsaggregator.data.datasource.remote.models.ArticleDto
import com.example.newsaggregator.data.datasource.remote.models.RssDto
import com.example.newsaggregator.data.datasource.toRequestResult
import javax.inject.Inject

class NewsRepository @Inject constructor(
    val newsApi: RssFeedApi,
    val database: NewsDatabase,
) {

    fun getAllNews(query: String) {

    }

    suspend fun getNewsFromServer(query: String): RequestResult<RssDto> {
        val remoteCall = newsApi.getRss(query).toRequestResult()
        return remoteCall
    }

    private suspend fun saveNewsAndCategoryToDatabase(data: List<ArticleDto>) {
        data.forEach { article ->
            val newsId = database.newsDao.insertNews(article.toArticleDbo())
            val categories = article.categories.map { it -> it.toCategoryDbo(newsId) }
            database.newsDao.insertCategories(categories)
        }
    }

    /*suspend fun getNewsFromDatabase(query: String): Flow<RequestResult<List<Article>>> {
        val dbRequset = database.newsDao.getNewsWithCategories(query)
    }*/
}