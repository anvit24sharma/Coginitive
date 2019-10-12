package com.reminder.cognitiveassignment.Activities

import Adapter.RecyclerViewAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reminder.cognitiveassignment.Activities.data.Post
import com.reminder.cognitiveassignment.Activities.data.Repository


class WeatherViewModel : ViewModel() {

    private var mutableLiveData: MutableLiveData<Post>? = null
    private var weatherRepository: Repository? = null

    fun init(cityname: String?) {
        if (mutableLiveData != null) {
            return
        }
        weatherRepository = Repository()


        mutableLiveData =
            weatherRepository!!.getWeatherReport(RecyclerViewAdapter.Statified.BASE_URL + "weather?q=" + cityname + RecyclerViewAdapter.Statified.Appid)

    }

    fun getWeatherRepository(): LiveData<Post>? {
        return mutableLiveData
    }

}