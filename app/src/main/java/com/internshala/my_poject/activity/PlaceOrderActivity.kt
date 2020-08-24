package com.internshala.my_poject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.internshala.my_poject.R
import kotlinx.android.synthetic.main.fragment_favourites.*

class PlaceOrderActivity : AppCompatActivity() {
    lateinit var toolbar  :Toolbar
    lateinit var ordering_from :TextView
    lateinit var rupees :TextView
    lateinit var total_rs :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "${recyclerRestaurants} name"

        ordering_from = findViewById(R.id.ordering_from)
        rupees = findViewById(R.id.rupees)
        total_rs =  findViewById(R.id.total_rs)


    }
}