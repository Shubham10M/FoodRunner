package com.internshala.my_poject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.DashboardFragment

class ConfirmActivity : AppCompatActivity() {
    lateinit var img_mark: ImageView
    lateinit var text_read  : TextView
    lateinit var button_ok : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        img_mark = findViewById(R.id.img_mark)
        text_read = findViewById(R.id.img_mark)
        button_ok = findViewById(R.id.button_ok)


        button_ok.setOnClickListener {
            startActivity(Intent(this, DashboardFragment::class.java))
        }
    }


}