package com.internshala.my_poject.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.ProfileFragment
import com.internshala.my_poject.util.Extentions.isEmailValid
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*


class RegisterActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var rlRegister: RelativeLayout
    private lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    lateinit var profileFragment: ProfileFragment
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()
        rlRegister = findViewById(R.id.rlRegister)

//        val refreshedToken: String = FirebaseInstanceId.getInstance().getToken()
//        Log.d(TAG, "Refreshed token: $refreshedToken")

        val btnRegister = findViewById<View>(R.id.btnRegister) as Button
        btnRegister.setOnClickListener {
            register()
        }

    }


    private fun register() {
        val name = etName.text.toString()
        val email = etEmail.text.toString()
        val mobile = etPhoneNumber.text.toString()
        val password = etPassword.text.toString()
        val cPassword = etConfirmPassword.text.toString()
        val address = etAddress.text.toString()

        if (TextUtils.isEmpty(name)) {
            etName.error = "Name cannot be empty"
            return
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.error = "Email cannot be empty"
            return
        }
        if (!email.isEmailValid()) {
            etEmail.error = "Invalid email"
            return
        }

        if (TextUtils.isEmpty(mobile)) {
            etPhoneNumber.error = "Mobile cannot be empty"
            return
        }

        if (mobile.length < 10) {
            etPhoneNumber.error = "Invalid mobile length"
            return
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Password cannot be empty"
            return
        }
        if (TextUtils.isEmpty(cPassword)) {
            etConfirmPassword.error = "Confirm password cannot be empty"
            return
        }

        if (password != cPassword) {
            Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth /*.signInWithEmailAndPassword(email, passward)*/
            .createUserWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString()
            )
            .addOnSuccessListener {
                // account creation successful, upate the UI and send an account verification email
                Log.e("TAG", "email is:----------------------------" + it.user?.email)
                Toast.makeText(this, "User created successfully", Toast.LENGTH_LONG).show()
                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String, Any> = HashMap()
                user["etName"] = etName
                user["etPhoneNumberr"] = etPhoneNumber
                user["etEmail"] = etEmail
                user["etAddress"] = etAddress

                db.collection("user")
                    .add(user).addOnSuccessListener {
                        Toast.makeText(this, " record added Succesfully", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, " record Failed to add", Toast.LENGTH_LONG).show()
                    }
                //   readFirebasedata()

                startActivity(Intent(this, LoginActivity::class.java))
            }
            .addOnFailureListener {
                Log.e("TAG", "error is:---------------------------- " + it.message)
                // account creation failed, probably the account already exists or bad network connection
                it.printStackTrace()
                Toast.makeText(this, "Error while creating user", Toast.LENGTH_LONG).show()
            }

    }

    fun readFirebasedata() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").get()
            .addOnCompleteListener {
                val result: StringBuffer = StringBuffer()
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("etName")).append(" ")
                        result.append(document.data.getValue("etPhoneNumber")).append(" ")
                        result.append(document.data.getValue("etEmail")).append(" ")
                        result.append(document.data.getValue("etAddress")).append("\n\n ")

                    }
                    txtUserName.text = result
                    txtPhone.text = result
                    txtEmail.text = result
                    txtAddress.text = result


                }

            }
    }
}
