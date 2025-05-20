package com.example.newsaggregator.di

import android.content.Context
import com.example.newsaggregator.feature_news_main.data.datasource.local.NewsDatabase
import com.example.newsaggregator.feature_news_main.data.datasource.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient): NewsApi {
        return NewsApi(
            baseUrl = "https://www.theguardian.com",
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase {
        return NewsDatabase(context)
    }

}