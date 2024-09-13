package com.project.animalface_app.ohjapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Base URL 설정 (API의 기본 주소)
    private const val BASE_URL = "https://your-api-url.com"

    // OkHttpClient 설정 (Optional: Interceptors 추가 가능)
    private val okHttpClient = OkHttpClient.Builder()
        .build()

    // Retrofit 인스턴스 생성
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())  // JSON 변환을 위한 Gson 사용
            .build()
    }

    // QuizService 인터페이스 구현체 반환
    val api: QuizService by lazy {
        retrofit.create(QuizService::class.java)
    }
}
