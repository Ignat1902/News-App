package com.example.newsaggregator.data.datasource.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryDBO(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long,
    val name: String,
    @ColumnInfo(name = "news_id")
    val newsId: Long
)
