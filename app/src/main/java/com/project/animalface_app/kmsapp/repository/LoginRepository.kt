package com.project.animalface_app.kmsapp.repository

import android.content.SharedPreferences
import com.project.animalface_app.kmsapp.retrofit.INetworkService
import com.project.animalface_app.kmsapp.dto.LoginRequest


class LoginRepository(private val apiService: INetworkService, private val sharedPreferences: SharedPreferences) {

    suspend fun login(username: String, password: String): Boolean {
        val loginRequest = LoginRequest(username, password)
        val response = apiService.login(loginRequest)

        return if (response.isSuccessful && response.body() != null) {
            val accessToken = response.body()?.accessToken
            val refreshToken = response.body()?.refreshToken
            val username = response.body()?.username

            // JWT 토큰을 SharedPreferences에 저장
            sharedPreferences.edit().putString("jwt_token", accessToken).apply()
            sharedPreferences.edit().putString("refreshToken", refreshToken).apply()
            sharedPreferences.edit().putString("username", username).apply()

            true
        } else {
            false
        }
    }
}