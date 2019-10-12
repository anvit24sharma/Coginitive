package Adapter

import Adapter.RecyclerViewAdapter.Statified.Appid
import Interfaces.ApiCallInterface
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.reminder.cognitiveassignment.Activities.WeatherDetailActivity
import com.reminder.cognitiveassignment.Activities.data.Post
import com.reminder.cognitiveassignment.R
import kotlinx.android.synthetic.main.recyclerviewitem.view.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RecyclerViewAdapter(val CityName : ArrayList<String> , val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    object Statified {
        val CitynameKey: String = "CityName"
        var BASE_URL = "https://api.openweathermap.org/data/2.5/"
        var Appid = "&appid=a11bb421d284ef61e5b0f02e2988ad64"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerviewitem,
            parent, false))
    }

    override fun getItemCount(): Int {
        return CityName.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.city_name.text = CityName[position]
        createRetrofitBuilder(CityName[position],holder)

        holder.itemView.linear.setOnClickListener {
            val intent = Intent(context,WeatherDetailActivity::class.java)
            intent.putExtra(Statified.CitynameKey,CityName[position])
            context.startActivity(intent)
        }
    }


    private fun createRetrofitBuilder(cityname: String, holder: ViewHolder) {

        val httpClient = OkHttpClient.Builder()

        val retrofit = Retrofit.Builder()
            .baseUrl(Statified.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        val postsApi = retrofit.create(ApiCallInterface::class.java)

        val call = postsApi.getWeatherdata(Statified.BASE_URL + "weather?q=" + cityname + Appid)

        Log.i("d", Statified.BASE_URL + "weather?q=" + cityname + Appid)

        call.enqueue(object : Callback<Post> {

            override fun onFailure(call: Call<Post>?, t: Throwable?) {
                Toast.makeText(context,"On Failure",Toast.LENGTH_SHORT).show()
                Log.i("On failure","Reason : "+t?.fillInStackTrace())
            }

            override fun onResponse(call: Call<Post>?, response: Response<Post>?) {
                Log.i("d", response?.body().toString())
                val basicweatherdata = response?.body()!!.weatherlist[0].description
                holder.itemView.city_weather.text = basicweatherdata
            }
        })
    }

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view)
}



