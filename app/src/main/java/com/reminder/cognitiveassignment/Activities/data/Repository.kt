package com.reminder.cognitiveassignment.Activities.data

import Adapter.RecyclerViewAdapter
import Interfaces.ApiCallInterface
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository {

    private val weatherApi: ApiCallInterface

    init {
        val httpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(RecyclerViewAdapter.Statified.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        weatherApi = retrofit.create(ApiCallInterface::class.java)


    }

    fun getWeatherReport(url: String): MutableLiveData<Post> {
        val weatherData = MutableLiveData<Post>()

        weatherApi.getWeatherdata(url).enqueue(object : Callback<Post> {
            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                if (response.isSuccessful) {
                    weatherData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
            }
        })
        return weatherData
    }

    companion object {

        private var weatherRepository: Repository? = null

        val instance: Repository
            get() {
                if (weatherRepository == null) {
                    weatherRepository = Repository()
                }
                return weatherRepository as Repository
            }
    }
}