package com.internshala.my_poject.activity

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.internshala.my_poject.R
import com.internshala.my_poject.fragment.ProfileFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import java.security.AlgorithmParameters.getInstance
import java.util.Calendar.getInstance


class RegisterActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var rlRegister: RelativeLayout
    private lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase : DatabaseReference
    lateinit var profileFragment : ProfileFragment
    val auth = FirebaseAuth.getInstance()

=======
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
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
<<<<<<< HEAD
        mAuth = FirebaseAuth.getInstance()
        rlRegister = findViewById(R.id.rlRegister)

//        val refreshedToken: String = FirebaseInstanceId.getInstance().getToken()
//        Log.d(TAG, "Refreshed token: $refreshedToken")

        val btnRegister = findViewById<View>(R.id.btnRegister) as Button
        btnRegister.setOnClickListener(View.OnClickListener { view ->
            register()
        })

}

  
    private fun register(){
        val etName = findViewById<View>(R.id.etName) as EditText
        val etEmail = findViewById<View>(R.id.etEmail) as EditText
        val etPhoneNumber = findViewById<View>(R.id.etPhoneNumber) as EditText
        val etAddress = findViewById<View>(R.id.etAddress) as EditText
        val etPassword = findViewById<View>(R.id.etPassword)as EditText
        val etConfirmPassward = findViewById<View>(R.id.etConfirmPassword)as EditText


            if(etName.text.isEmpty()){
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_LONG).show()
                return
            }
            if(etEmail.text.isEmpty()){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show()
                return
            }

        if(etPassword.text.isEmpty()){
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        if(etPhoneNumber.text.isEmpty()){
            Toast.makeText(this, "PhoneNumber cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        if(etAddress.text.isEmpty()){
            Toast.makeText(this, "Address cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        if(etConfirmPassward.text.isEmpty()){
            Toast.makeText(this, "confirm passward cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
//                val bundle = Bundle()
//                bundle.putString("data", "register")
//                bundle.putString("name", etName.text.toString())
//                bundle.putString("mobile", etPhoneNumber.text.toString())
//                bundle.putString("password", etPassword.text.toString())
//                bundle.putString("address", etAddress.text.toString())
//                bundle.putString("email", etEmail.text.toString())
//                bundle.putString("Passward", etPassword.text.toString())
//                bundle.putString("ConfirmPassward", etConfirmPassward.text.toString())
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
                        val user : MutableMap<String, Any> = HashMap()
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
                        Log.e("TAG", "error is:---------------------------- " + it.message);
                        // account creation failed, probably the account already exists or bad network connection
                        it.printStackTrace()
                        Toast.makeText(this, "Error while creating user", Toast.LENGTH_LONG).show()
                    }
//
//            }
//            // to check both the passward entered is same or not
//        else if(etPassword != etConfirmPassward){
//            Toast.makeText(this, "Both Passward does not match. Please check! ", Toast.LENGTH_LONG).show()
//        }
//            else{
//                Toast.makeText(this, " Please fill up the credentials", Toast.LENGTH_LONG).show()
//            }
        }

  //  }
=======

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
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a

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
                            //save into the preferences and finish this activity.
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
                    setResult(RESULT_OK)
                    finish()
                }, { _, _ ->

                })
            }
        }
    }
     fun readFirebasedata(){
         val db = FirebaseFirestore.getInstance()
         db.collection("users").get()
             .addOnCompleteListener {
                 val result :  StringBuffer = StringBuffer()
                 if(it.isSuccessful){
                     for (document in it.result!!){
                         result.append(document.data.getValue("etName")).append(" ")
                         result.append(document.data.getValue("etPhoneNumber")).append(" ")
                         result.append(document.data.getValue("etEmail")).append(" ")
                         result.append(document.data.getValue("etAddress")).append("\n\n ")

                     }
                     txtUserName.setText(result)
                     txtPhone.setText(result)
                     txtEmail.setText(result)
                     txtAddress.setText(result)


                 }

            }
     }
}
