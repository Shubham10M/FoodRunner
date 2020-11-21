package com.internshala.my_poject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.internshala.my_poject.R
import kotlinx.android.synthetic.main.activity_forgot_passward.*
import kotlinx.android.synthetic.main.activity_register.*

class ForgotPasswardActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passward)
//
//        etForgotMobile = findViewById(R.id.etForgotMobile)
//        etForgotEmail = findViewById(R.id.etForgotEmail)
//        btnForgotNext = findViewById(R.id.btnForgotNext)
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Name2")
        val btnForgotNext = findViewById<View>(R.id.btnForgotNext) as Button


        btnForgotNext.setOnClickListener {
            forgotPassward()
            auth.sendPasswordResetEmail(etForgotEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, " Email Sent", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }

    private fun forgotPassward() {
        if (etForgotEmail.text.toString().isEmpty()) {
            Toast.makeText(this, " fill the credentials ", Toast.LENGTH_LONG).show()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etForgotEmail.text.toString()).matches()) {
            return
        }

    }
}