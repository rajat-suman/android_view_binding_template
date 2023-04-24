package com.viewBindingTemplate.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.viewBindingTemplate.BuildConfig
import com.viewBindingTemplate.remote.APIRequest
import com.viewBindingTemplate.remote.ConnectionInterceptor
import com.viewBindingTemplate.remote.IgnoreFailureTypeAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    /**
     * Save Data in Cache
     * */
    @Provides
    @Singleton
    fun cache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, (5 * 1024 * 1024).toLong()) // 5MB of Cache Size

    @Singleton
    @Provides
    fun provideOkHttpClient(
        cache: Cache,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES).addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true).cache(cache)
            .addInterceptor(ConnectionInterceptor.interceptor).build()
    }

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().setLenient().registerTypeAdapterFactory(IgnoreFailureTypeAdapterFactory()).create()

    @Singleton
    @Provides
    fun gsonConverterFactory(
        gson: Gson,
    ): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        BASE_URL: String,
    ): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke()).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): APIRequest = retrofit.create(APIRequest::class.java)

    @Provides
    @Singleton
    fun exceptionHandler() = CoroutineExceptionHandler { _, t ->
        t.printStackTrace()

        CoroutineScope(Dispatchers.Main).launch {
            t.printStackTrace()
        }
    }
}