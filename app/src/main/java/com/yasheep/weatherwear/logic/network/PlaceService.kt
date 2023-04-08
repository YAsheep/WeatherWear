package com.yasheep.weatherwear.logic.network

import com.yasheep.weatherwear.WeatherWearApplication
import com.yasheep.weatherwear.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("v2/place?token=${WeatherWearApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

}