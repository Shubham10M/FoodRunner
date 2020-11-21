package com.internshala.my_poject.activity

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
