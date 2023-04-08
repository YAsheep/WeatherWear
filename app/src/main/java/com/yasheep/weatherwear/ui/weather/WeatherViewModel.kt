package com.yasheep.weatherwear.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.yasheep.weatherwear.logic.Repository
import com.yasheep.weatherwear.logic.model.Location

/**
 * 需要提供获取天气的方法
 * 需要存储经纬度信息
 */
class WeatherViewModel: ViewModel() {
    // ？为何私有,这里的逻辑是借助Location去获取天气，
    // 因为每次返回的天气对象都是新的，不应该直接返回LiveData对象，否则无法监听-->使得仓库层返回的LiveData对象转换成一个可供Activity观察的LiveData对象？
    private val locationLiveData = MutableLiveData<Location>()

    //和界面相关的数据 防止丢失
    var locationLng = ""
    var locationLat = ""
    var placeName = ""

    val weatherLiveData = locationLiveData.switchMap {
        location -> Repository.getWeather(location.lng, location.lat)
    }

    fun refreshWeather(lng: String, lat: String){
        locationLiveData.value = Location(lng, lat)
    }
}