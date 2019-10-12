package com.reminder.cognitiveassignment.Activities

import Adapter.RecyclerViewAdapter
import Adapter.RecyclerViewAdapter.Statified.Appid
import Adapter.RecyclerViewAdapter.Statified.BASE_URL
import Interfaces.ApiCallInterface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.reminder.cognitiveassignment.Activities.data.Post
import com.reminder.cognitiveassignment.R
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class WeatherDetailActivity : AppCompatActivity() {

    lateinit var cityName : TextView
    lateinit var description : TextView
    lateinit var currTemp : TextView
    lateinit var minTemp : TextView
    lateinit var maxTemp : TextView
    lateinit var humdity : TextView
    var cityname : String? = null
    var lat : String? =null
    var long : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        supportActionBar?.hide()
        intializeView()

        cityname = intent.getStringExtra(RecyclerViewAdapter.Statified.CitynameKey)
        lat = intent.getStringExtra(LatLongActvity.Statified.Lat)
        long = intent.getStringExtra(LatLongActvity.Statified.longi)


        if(lat == null && long == null) {
            cityName.text = cityname
            createRetrofitWithViewModel(cityname)
        } else
            createRetrofitBuilderLatLong(lat,long)


    }

    private fun createRetrofitWithViewModel(cityname: String?) {

        val weatherViewModel: WeatherViewModel =
            ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        weatherViewModel.init(cityname)


        weatherViewModel.getWeatherRepository()?.observe(this, object : Observer<Post> {
            override fun onChanged(t: Post?) {
                val basicweatherdata = t?.weatherlist!![0].description
                val Temp: String = Math.round(t.main.temp - 273.15).toString() + 0x00B0.toChar() + "C"
                val MTemp: String = Math.round(t.main.temp_min - 273.15).toString() + 0x00B0.toChar() + "C"
                val MxTemp: String = Math.round(t.main.temp_max - 273.15).toString() + 0x00B0.toChar() + "C"
                val Humid: String = t.main.humidity.toString() + "%"

                description.text=basicweatherdata
                currTemp.text = Temp
                minTemp.text = MTemp
                maxTemp.text = MxTemp
                humdity.text = Humid
            }


        })

    }

    private fun createRetrofitBuilderLatLong( Lat :String?, Long :String?)  {

        val httpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        val postsApi = retrofit.create(ApiCallInterface::class.java)

        val call = postsApi.getWeatherdata(BASE_URL + "weather?" + "lat="+ Lat +"&lon="+Long + Appid)


        call.enqueue(object : Callback<Post> {

            override fun onFailure(call: Call<Post>?, t: Throwable?) {
                // Toast.makeText(,"On Failure", Toast.LENGTH_SHORT).show()
                Log.i("On failure","Reason : "+t?.fillInStackTrace())

            }

            override fun onResponse(call: Call<Post>?, response: Response<Post>?) {


                val basicweatherdata = response?.body()!!.weatherlist[0].description
                val Temp :String =Math.round(response.body()!!.main.temp-273.15).toString() + 0x00B0.toChar() +"C"
                val MTemp :String =Math.round(response.body()!!.main.temp_min-273.15).toString() + 0x00B0.toChar() +"C"
                val MxTemp :String =Math.round(response.body()!!.main.temp_max-273.15).toString() + 0x00B0.toChar() +"C"
                val Humid :String = response.body()!!.main.humidity.toString() +"%"

                cityName.text = response.body()!!.name
                description.text=basicweatherdata
                currTemp.text = Temp
                minTemp.text = MTemp
                maxTemp.text = MxTemp
                humdity.text = Humid

            }

        })

    }

    private fun intializeView() {
        cityName = findViewById(R.id.city_name)
        description = findViewById(R.id.basic_desc)
        currTemp = findViewById(R.id.current_temp)
        minTemp = findViewById(R.id.min_temp)
        maxTemp = findViewById(R.id.max_temp)
        humdity = findViewById(R.id.humidity)

    }
}
