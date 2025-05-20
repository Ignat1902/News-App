package com.example.newsaggregator.feature_news_main.data.datasource.remote.models

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("content", "http://search.yahoo.com/mrss/", "media")
data class ContentDto (
    val type: String?,
    val width: String?,
    val url: String,
    val credit: CreditDto?,
)