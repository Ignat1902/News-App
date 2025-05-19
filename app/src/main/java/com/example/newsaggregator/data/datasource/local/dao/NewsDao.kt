package com.example.newsaggregator.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsaggregator.data.datasource.local.models.ArticleDBO
import com.example.newsaggregator.data.datasource.local.models.ArticleWithCategories
import com.example.newsaggregator.data.datasource.local.models.CategoryDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Transaction
    @Query("SELECT * FROM News WHERE title LIKE '%' || :query || '%'")
    suspend fun getNewsWithCategories(query: String): List<ArticleWithCategories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsWithCategories: ArticleDBO): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryDBO>)

}