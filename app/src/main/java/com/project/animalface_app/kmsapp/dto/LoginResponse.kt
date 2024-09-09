package com.project.animalface_app.kmsapp.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val memberId: String
)