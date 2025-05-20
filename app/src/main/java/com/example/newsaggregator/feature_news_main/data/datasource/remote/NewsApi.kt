package com.example.newsaggregator.feature_news_main.data.datasource.remote

import com.example.newsaggregator.feature_news_main.data.datasource.remote.models.RssDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {
    @GET("/{query}/rss")
    suspend fun getRss(
        @Path("query") query: String? = "international"
    ): Result<RssDto>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): NewsApi = retrofit(baseUrl, okHttpClient).create(NewsApi::class.java)

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null
): Retrofit {
    val contentType = "application/xml; charset=UTF8".toMediaType()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .addConverterFactory(
            XML.asConverterFactory(contentType)
        )
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()
}

//https://www.theguardian.com