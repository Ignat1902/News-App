package com.example.newsaggregator.feature_news_main.data.repository

import com.example.newsaggregator.core.util.RequestResult
import com.example.newsaggregator.feature_news_main.data.datasource.local.NewsDatabase
import com.example.newsaggregator.feature_news_main.data.datasource.remote.NewsApi
import com.example.newsaggregator.feature_news_main.data.datasource.remote.models.ArticleDto
import com.example.newsaggregator.feature_news_main.data.repository.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    val newsApi: NewsApi,
    val database: NewsDatabase,
) {
    fun getNews(query: String?): Flow<RequestResult<List<Article>>> = flow {
        emit(RequestResult.Loading())

        val newsDB = database.newsDao.getNewsWithCategories().map { it.toArticle() }
        emit(RequestResult.Loading(data = newsDB))

        newsApi.getRss(query)
            .onSuccess {
                val remoteNews = it.channel.items
                database.newsDao.clearNews()
                saveNewsAndCategoryToDatabase(remoteNews)
            }
            .onFailure {
                when (it) {
                    is IOException -> {
                        emit(
                            RequestResult.Error(
                                message = "Couldn't reach server, check your internet connection.",
                                data = newsDB
                            )
                        )
                    }

                    is HttpException -> emit(
                        RequestResult.Error(
                            "Something went wrong",
                            data = newsDB
                        )
                    )
                }
            }

        val newNews = database.newsDao.getNewsWithCategories().map { it.toArticle() }
        emit(RequestResult.Success(newNews))
    }

    private suspend fun saveNewsAndCategoryToDatabase(data: List<ArticleDto>) {
        data.forEach { article ->
            val newsId = database.newsDao.insertNews(article.toArticleDbo())
            val categories = article.categories.map { it -> it.toCategoryDbo(newsId) }
            database.newsDao.insertCategories(categories)
        }
    }
}
