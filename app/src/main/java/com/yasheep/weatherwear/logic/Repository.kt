package com.yasheep.weatherwear.logic

import androidx.lifecycle.liveData
import com.yasheep.weatherwear.logic.model.Place
import com.yasheep.weatherwear.logic.model.Weather
import com.yasheep.weatherwear.logic.network.WeatherWearNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object Repository {
    //参数类型指定成了Dispatchers.IO，这样代码块中的所有代码就都运行在子线程中了
    fun searchPlaces(query: String) = fire<List<Place>>(Dispatchers.IO){
        val placeResponse = WeatherWearNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places) //使用Kotlin内置的Result.success()方法来包装获取的城市数据列表
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }


    fun getWeather(lng: String, lat: String, dailysteps: Int = 5) = fire<Weather>(Dispatchers.IO) {
        // 创建协程作用域才可调用async
        coroutineScope{// 需要async才可以并行
            val deferredRealtimeResponse = async { WeatherWearNetwork.getWeatherRealtime(lng, lat) }

            val deferredDailyResponse = async{ WeatherWearNetwork.getWeatherDaily(lng, lat, dailysteps) }

            //调用await获取返回,await()阻塞当前协程，等待结果返回
            val realtimeResponse = deferredRealtimeResponse.await()
            val dailyResponse = deferredDailyResponse.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val realtime = realtimeResponse.result.realtime
                val daily = dailyResponse.result.daily
                Result.success(Weather(realtime, daily))
            } else {
                Result.failure(RuntimeException("response status is ${realtimeResponse.status} and ${dailyResponse.status}"))
            }
        }
    }

    fun getWeather1(lng: String, lat: String, dailysteps: Int = 5) = fire(Dispatchers.IO) {
            val realtimeResponse = WeatherWearNetwork.getWeatherRealtime(lng, lat)
            if (realtimeResponse.status == "ok"){
                Result.success(realtimeResponse.result.realtime)
            }else{
                Result.failure(RuntimeException("response status is ${realtimeResponse.status}"))
            }
    }

    // 统一代码处理
    /** 在liveData()函数的代码块中，我们是拥有挂起函数上下文的，
        可是当回调到Lambda表达式中，代码就没有挂起函数上下文了，
        但实际上Lambda表达式中的代码一定也是在挂起函数中运行的。
        为了解决这个问题，我们需要在函数类型前声明一个suspend关键字，
     以表示所有传入的Lambda表达式中的代码也是拥有挂起函数上下文的。**/
    private fun <T> fire(context: CoroutineContext, block: suspend ()->Result<T>) = liveData<Result<T>>(context) {
        val result = try {
            block()
        }catch (e:Exception){
            Result.failure(e)
        }
        //emit()方法其实类似于调用LiveData的setValue()方法来通知数据变化，
        // 只不过这里我们无法直接取得返回的LiveData对象，所以lifecycle-livedata-ktx库提供了这样一个替代方法。
        emit(result)
    }

}