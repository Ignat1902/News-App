package com.example.newsaggregator.data.datasource.remote

import com.example.newsaggregator.data.datasource.remote.models.RssDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface RssFeedApi {
    @GET("/{query}/rss")
    suspend fun getRss(
        @Path("query") query: String = "international"
    ): Result<RssDto>
}

fun newsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): RssFeedApi = retrofit(baseUrl, okHttpClient).create(RssFeedApi::class.java)

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .run { if (okHttpClient != null) client(okHttpClient) else this }
        .addConverterFactory(
            XML.asConverterFactory(
                MediaType.get("application/xml; charset=UTF8")
            )
        ).build()
}

//https://www.theguardian.com