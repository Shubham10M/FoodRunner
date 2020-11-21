package com.internshala.my_poject.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.DashboardFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_forgot_passward.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.etEmail
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    private lateinit var txtRegisterYourself: TextView
    private lateinit var login: Button
    private lateinit var etMobileNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var txtForgotPassword: TextView
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var mAuth: FirebaseAuth
    val auth = FirebaseAuth.getInstance()
    var verificationId = " "
//    var loginId: String? = "102"

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()

        // etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegisterYourself = findViewById(R.id.txtRegisterYourself)
        login = findViewById(R.id.btnLogin)


        txtForgotPassword.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            builder.setTitle("Forgot Passward")
            startActivity(Intent(this, ForgotPasswardActivity::class.java))

           // val view = layoutInflater.inflate(R.layout.dialog_forgot_passward, null)
//            val email = view.findViewById<EditText>(R.id.etEmail)
//            builder.setView(view)
//            builder.setPositiveButton("Reset", DialogInterface.OnClickListener { _, _ ->
//                forgotPassward(email)
//            })
//            builder.setNegativeButton("Close", DialogInterface.OnClickListener { _, _ ->
//             builder.show()
//            })
        }

        txtRegisterYourself.setOnClickListener(View.OnClickListener { View ->
            Register()
        })
        login.setOnClickListener(View.OnClickListener { View ->
            login()
        })
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        if (!email.isEmpty() && !password.isEmpty()) {
            // get access to an instance of FirebaseAuth and perform login
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    // login successful, update the UI
                    Log.e("TAG", "--------------------success---------------" + it.user?.email)
                }
                .addOnFailureListener {
                    // login failed, probably bad email-password combo or a network issue
                    Log.e("TAG", "--------error---------------------------" + it.message)
                    it.printStackTrace()
                }
                .addOnCompleteListener {
                    Log.e("TAG", "OnComplete--> " + it.isSuccessful)

                    startActivity(Intent(this, DashboardActivity::class.java))

                }
        } else {
            Toast.makeText(this, " Please fill up the credentials", Toast.LENGTH_LONG).show()
        }
    }

    private fun Register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }



}
