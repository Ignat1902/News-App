package com.example.newsaggregator.data.datasource.local.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class ArticleDBO(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "link")
    val link: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "pub_date")
    val pubDate: String,
    @ColumnInfo(name = "creator")
    val creator: String,
)

data class Content(
    val width: String?,
    val url: String,
)
