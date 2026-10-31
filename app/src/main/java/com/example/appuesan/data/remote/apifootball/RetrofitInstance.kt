package com.example.appuesan.data.remote.apifootball

import com.example.appuesan.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = BuildConfig.API_BASE_URL
private const val API_KEY = BuildConfig.API_KEY

object RetrofitInstance{
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-apisports-key", API_KEY)
                .build()
            chain.proceed(newRequest)
        }
        .build()
    val api: ApiFootballService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiFootballService::class.java)
    }
}