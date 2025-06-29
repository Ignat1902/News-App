package com.example.newsaggregator.feature_news_main.data.datasource.remote.models

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@XmlSerialName("image")
data class ImageDto(
    @XmlSerialName("title")
    @XmlElement(true)
    val title: String,

    @XmlSerialName("url")
    @XmlElement(true)
    val url: String,

    @XmlSerialName("link")
    @XmlElement(true)
    val link: String
)
