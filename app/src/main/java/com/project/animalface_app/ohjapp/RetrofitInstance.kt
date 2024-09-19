package com.project.animalface_app.ohjapp

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://your-api-url.com"

    private val client = OkHttpClient.Builder().build()

    private val gson = GsonBuilder()
        .setLenient()  // lenient 모드 활성화
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))  // lenient Gson 적용
            .build()
    }

    val api: QuizService by lazy {
        retrofit.create(QuizService::class.java)
    }
}
