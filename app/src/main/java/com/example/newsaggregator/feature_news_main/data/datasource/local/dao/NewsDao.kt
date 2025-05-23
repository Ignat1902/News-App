package com.example.newsaggregator.feature_news_main.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.ArticleDBO
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.ArticleWithCategories
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.CategoryDBO

@Dao
interface NewsDao {

    @Transaction
    @Query("SELECT * FROM News")
    suspend fun getNewsWithCategories(): List<ArticleWithCategories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsWithCategories: ArticleDBO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryDBO>)

    @Query("DELETE FROM News")
    suspend fun clearNews()

    @Query("DELETE FROM Categories")
    suspend fun clearCategories()

    @Transaction
    suspend fun clearAll() {
        clearCategories()
        clearNews()
    }

    @Transaction
    @Query(
        """
    SELECT * FROM News
    WHERE title LIKE '%' || :query || '%'
       OR description LIKE '%' || :query || '%'
    """
    )
    suspend fun searchNews(query: String): List<ArticleWithCategories>

}