package com.reminder.cognitiveassignment.Activities.data

import com.google.gson.annotations.SerializedName

data class Post (

    @SerializedName("weather")
    val weatherlist: ArrayList<Weather>,

    @SerializedName("main")
    val main : Main,

    @SerializedName("name")
    val name : String

    )

data class Weather(
    @SerializedName("id")
    val id :Int,

    @SerializedName("main")
    val main :String,

    @SerializedName("description")
    val description :String
)

data class Main(
    @SerializedName("temp")
    val temp :Double,

    @SerializedName("humidity")
    val humidity :Int,

    @SerializedName("temp_min")
    val temp_min :Double,

    @SerializedName("temp_max")
    val temp_max :Double
)