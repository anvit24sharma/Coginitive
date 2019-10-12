package com.reminder.cognitiveassignment.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.reminder.cognitiveassignment.R

class LatLongActvity : AppCompatActivity() {

    lateinit var lat : EditText
    lateinit var longitude : EditText
    lateinit var search : Button

    object  Statified{
        const val Lat: String = "Latitude"
        const val longi: String = "Longitude"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lat_long)

        intializeView()


        search.setOnClickListener {

            if(!lat.text.equals("") && !longitude.text.equals("")){
                val intent  = Intent(this, WeatherDetailActivity::class.java)

                intent.putExtra(Statified.Lat,lat.text.toString())
                intent.putExtra(Statified.longi,longitude.text.toString())
                startActivity(intent)
            }
            else
                Toast.makeText(this,"Enter Values",Toast.LENGTH_SHORT).show()

        }


    }
    private fun intializeView() {
        lat = findViewById(R.id.lat)
        longitude = findViewById(R.id.longitude)
        search = findViewById(R.id.search)


    }
}
