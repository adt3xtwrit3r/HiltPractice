package com.bd.dana.hiltpractice.di

import android.app.Application
import com.bd.dana.hiltpractice.BuildConfig
import com.bd.dana.hiltpractice.api.RetrofitUtils.retrofitInstance
import com.bd.dana.hiltpractice.api.endpoint.ApiService
import com.bd.dana.hiltpractice.api.endpoint.ApiService2
import com.bd.dana.hiltpractice.helper.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("apiMovies")
    fun provideBaseUrlMovies1() = Constants.BASE_URL

    @Provides
    @Named("apiMovies2")
    fun provideBaseUrlMovies2() = Constants.BASE_URL2

    @Provides
    @Singleton
    fun provideRetrofitInstance1(@Named("apiMovies") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance2(@Named("apiMovies2") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService2 =
        retrofitInstance(baseUrl = BASE_URL, gson, httpClient)
            .create(ApiService2::class.java)

}