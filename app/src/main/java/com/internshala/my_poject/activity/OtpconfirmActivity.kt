package com.internshala.my_poject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.internshala.my_poject.R

class OtpconfirmActivity : AppCompatActivity() {

    lateinit var textotp :TextView
    lateinit var etotpenter :EditText
    lateinit var etnewPassward :EditText
    lateinit var etConfirmPassward :EditText
    lateinit var btnSubmit : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpconfirm)

        textotp = findViewById(R.id.textotp)
        etotpenter = findViewById(R.id.etotpenter)
        etnewPassward = findViewById(R.id.etnewPassward)
        etConfirmPassward = findViewById(R.id.etConfirmPassword)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener{
            val intent = Intent(this@OtpconfirmActivity, LoginActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data", "otp")
            bundle.putString("otp", etotpenter.text.toString())
            bundle.putString("mobile", etnewPassward.text.toString())
            bundle.putString("password", etConfirmPassward.text.toString())
            intent.putExtra("details", bundle)
            startActivity(intent)

        }
    }
}