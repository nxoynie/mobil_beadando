package com.example.calorieintaketracker.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface WebContentService {
    @GET("/jod")
    fun getJoke(): Call<ResponseBody>
}