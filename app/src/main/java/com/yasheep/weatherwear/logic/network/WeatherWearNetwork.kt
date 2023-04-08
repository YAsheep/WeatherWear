package com.yasheep.weatherwear.logic.network

import retrofit2.await

object WeatherWearNetwork {
    // 使用ServiceCreator创建了一个PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create<PlaceService>()

    // 可以看一下await()的实现 启动了协程
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private val weatherService = ServiceCreator.create<WeaterService>()

    suspend fun getWeatherRealtime(lng: String, lat: String)
    = weatherService.getRealtime(lng, lat).await()

    suspend fun getWeatherDaily(lng: String, lat: String, dailysteps: Int)
    = weatherService.getDaily(lng, lat, dailysteps).await()

}