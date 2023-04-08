package com.yasheep.weatherwear.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
//import androidx.lifecycle.Transformations
import com.yasheep.weatherwear.logic.Repository
import com.yasheep.weatherwear.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    // Kotlin code must now use the Kotlin extension method syntax
//    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
//        Repository.searchPlaces(query)
//    }
    val placeLiveData = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

}