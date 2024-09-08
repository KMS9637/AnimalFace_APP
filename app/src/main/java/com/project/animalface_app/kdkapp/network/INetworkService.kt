package com.project.animalface_app.kdkapp.network


import com.project.animalface_app.kdkapp.dto.PredictionResult
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface INetworkService {
    @Multipart
    @POST("/classify")
    fun predictImage(
        @Part image: MultipartBody.Part
    ): Call<PredictionResult>


}