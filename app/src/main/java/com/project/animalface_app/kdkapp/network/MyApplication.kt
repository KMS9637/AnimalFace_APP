package com.appliances.recycle.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.project.animalface_app.kdkapp.network.INetworkService

object MyApplication {
    private val BASE_URL = "http://10.100.201.42:8080/" // localhost 대신 사용
    private lateinit var apiService: INetworkService

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun getApiService(): INetworkService {
        return apiService
    }

    val instance: INetworkService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(INetworkService::class.java)
    }
}