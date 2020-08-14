package com.internshala.my_poject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.DashboardFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var txtRegisterYourself: TextView
    private lateinit var login: Button
    private lateinit var etMobileNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtForgotPassword: TextView

    var loginId: String? = "102"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_login)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegisterYourself = findViewById(R.id.txtRegisterYourself)
        login = findViewById(R.id.btnLogin)

        if (intent != null) {
            loginId = intent.getStringExtra("restaurantid")
        } else {
            finish()
            Toast.makeText(
                this@LoginActivity,
                "some unexpected error occured",
                Toast.LENGTH_SHORT
            ).show()
        }
        if (loginId == "100") {
            finish()
            Toast.makeText(
                this@LoginActivity,
                "some unexpected error occured",
                Toast.LENGTH_SHORT
            ).show()
        }

        txtForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswardActivity::class.java)
            startActivity(intent)
        }
        txtRegisterYourself.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)

        }
        login.setOnClickListener {

            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intent)
        }

    }
}
