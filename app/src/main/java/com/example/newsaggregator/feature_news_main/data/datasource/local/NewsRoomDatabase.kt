package com.example.newsaggregator.feature_news_main.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsaggregator.feature_news_main.data.datasource.local.dao.NewsDao
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.ArticleDBO
import com.example.newsaggregator.feature_news_main.data.datasource.local.models.CategoryDBO

class NewsDatabase(
    private val database: NewsRoomDatabase
) {
    val newsDao: NewsDao
        get() = database.newsDao()
}

@Database(entities = [ArticleDBO::class, CategoryDBO::class], version = 1)
abstract class NewsRoomDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

fun NewsDatabase(applicationContext: Context): NewsDatabase {
    val roomDB = Room.databaseBuilder(
        applicationContext,
        NewsRoomDatabase::class.java,
        "news_database"
    ).build()
    return NewsDatabase(roomDB)
}