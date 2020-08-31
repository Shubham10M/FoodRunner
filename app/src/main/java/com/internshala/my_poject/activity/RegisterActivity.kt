package com.internshala.my_poject.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.internshala.my_poject.R
import com.internshala.my_poject.base.BaseActivity
import com.internshala.my_poject.model.RegisterRequest
import com.internshala.my_poject.model.UserProfile
import com.internshala.my_poject.util.ConnectionManager
import com.internshala.my_poject.util.Extentions.isEmailValid
import com.internshala.my_poject.util.Utils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val mobile = etPhoneNumber.text.toString()
            val password = etPassword.text.toString()
            val cPassword = etConfirmPassword.text.toString()
            val address = etAddress.text.toString()

            if (TextUtils.isEmpty(name)) {
                etName.error = "Name cannot be empty"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                etEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (!email.isEmailValid()) {
                etEmail.error = "Invalid email"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(mobile)) {
                etPhoneNumber.error = "Mobile cannot be empty"
                return@setOnClickListener
            }

            if (mobile.length < 10) {
                etPhoneNumber.error = "Invalid mobile length"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                etPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(cPassword)) {
                etConfirmPassword.error = "Confirm password cannot be empty"
                return@setOnClickListener
            }

            if (password != cPassword) {
                Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = RegisterRequest(name, email, mobile, password, address)

            if (ConnectionManager().isNetworkAvailable(this)) {
                clLoading.visibility = View.VISIBLE
                val call = request.registerUser(user)
                call.enqueue(object : Callback<UserProfile> {
                    override fun onResponse(
                        call: Call<UserProfile>,
                        response: Response<UserProfile>
                    ) {
                        clLoading.visibility = View.GONE
                        if (response.isSuccessful) {
                            Log.e("Register Activity", response.body()?.data.toString())
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Failed to register user",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                        clLoading.visibility = View.GONE
                        Toast.makeText(
                            this@RegisterActivity,
                            "Failed to register user Reason: $t",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                })
            } else {
                Utils.noInternetDialog(this, { _, _ ->
                    finish()
                }, { _, _ ->

                })
            }
            /*val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            val bundle = Bundle()
            bundle.putString("data", "register")
            bundle.putString("name", etName.text.toString())
            bundle.putString("mobile", etPhoneNumber.text.toString())
            bundle.putString("password", etPassword.text.toString())
            bundle.putString("address", etAddress.text.toString())
            intent.putExtra("details", bundle)
            startActivity(intent)*/
        }
    }
}
