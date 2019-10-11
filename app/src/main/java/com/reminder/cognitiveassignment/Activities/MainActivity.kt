package com.reminder.cognitiveassignment.Activities

import Adapter.RecyclerViewAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.reminder.cognitiveassignment.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var cities : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        addCities()

        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = DefaultItemAnimator()
        recycler_view.adapter = RecyclerViewAdapter(cities,this)

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.searchmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       if(R.id.Search == item?.itemId){
           startActivity(Intent(this,LatLongActvity::class.java))
       }

        return super.onOptionsItemSelected(item)
    }

    private fun addCities() {
        cities = ArrayList()

        cities.add("Bangalore")
        cities.add("Mumbai")
        cities.add("Delhi")
        cities.add("Kolkata")
        cities.add("Chennai")
        cities.add("Hyderabad")
        cities.add("Lucknow")
        cities.add("Bhopal")
        cities.add("Chandigarh")
    }

}
