package com.bd.dana.hiltpractice.di

import android.app.Application
import com.bd.dana.hiltpractice.BuildConfig
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
    fun getGson(): Gson {
        return GsonBuilder().setLenient().setPrettyPrinting().create()
    }
    @Provides
    @Singleton
    fun createCache(application: Application): Cache {
        val cacheSize = 5L * 1024L * 1024L // 5 MB
        return Cache(File(application.cacheDir, "${application.packageName}.cache"), cacheSize)
    }

    @Provides
    @Singleton
    fun createOkHttpClient(cache: Cache?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                val logging =
                    httpLoggingInterceptor.apply {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    }
                addInterceptor(logging)
            }
            cache(cache)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(1, TimeUnit.MINUTES)
            connectTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance1(@Named("apiMovies") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofitInstance2(@Named("apiMovies2") BASE_URL: String, gson: Gson, httpClient: OkHttpClient): ApiService2 =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(httpClient)
            .build()
            .create(ApiService2::class.java)

}