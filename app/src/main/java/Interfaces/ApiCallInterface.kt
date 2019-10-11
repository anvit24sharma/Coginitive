package Interfaces

import com.reminder.cognitiveassignment.Activities.data.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface ApiCallInterface {
    @GET
    fun getWeatherdata(@Url url:String): Call<Post>
}