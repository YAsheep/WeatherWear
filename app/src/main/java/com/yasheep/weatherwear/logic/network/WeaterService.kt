package com.yasheep.weatherwear.logic.network

import com.yasheep.weatherwear.WeatherWearApplication
import com.yasheep.weatherwear.logic.model.DailyResponse
import com.yasheep.weatherwear.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeaterService {
    @GET("v2.6/${WeatherWearApplication.TOKEN}/{lng},{lat}/realtime")
    fun getRealtime(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>
//
    @GET("v2.6/${WeatherWearApplication.TOKEN}/{lng},{lat}/daily?")
    fun getDaily(@Path("lng") lng: String, @Path("lat") lat: String, @Query("dailysteps") dailysteps:Int)
    :Call<DailyResponse>
//    @GET("v2.5/${WeatherWearApplication.TOKEN}/{lng},{lat}/realtime.json")
//    fun getRealtime(@Path("lng") lng: String, @Path("lat") lat: String):
//            Call<RealtimeResponse>

//    @GET("v2.5/${WeatherWearApplication.TOKEN}/{lng},{lat}/daily.json")
//    fun getDaily(@Path("lng") lng: String, @Path("lat") lat: String):
//            Call<DailyResponse>

}