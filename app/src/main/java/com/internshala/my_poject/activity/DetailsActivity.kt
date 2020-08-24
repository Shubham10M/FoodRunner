package com.internshala.my_poject.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.internshala.my_poject.R
import com.internshala.my_poject.api.ApiClient
import com.internshala.my_poject.api.ApiInterface
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
import com.internshala.my_poject.model.Example
import com.internshala.my_poject.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    var restaurantId: String? = null
    private val request = ApiClient.buildService(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

//        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Restaurant Details"

//        textmenu = findViewById(R.id.textmenu)
//        menuIcon = findViewById(R.id.menuIcon)
//        textNo = findViewById(R.id.textNo)
//        textRestro1 = findViewById(R.id.textRestro1)
//        progressBar = findViewById(R.id.progressBar)
//        progressBar.visibility = View.VISIBLE
//        progressLayout = findViewById(R.id.progressLayout)
//        progressLayout.visibility = View.VISIBLE
//        btnadd = findViewById(R.id.btnadd)
//        btnaddTocart = findViewById(R.id.btnaddTocart)
//        textcost = findViewById(R.id.textCost)

        btnaddTocart.setOnClickListener {
            val intent = Intent(this@DetailsActivity, PlaceOrderActivity::class.java)
            startActivity(intent)
        }

        if (intent != null && intent.hasExtra("id")) {
            restaurantId = intent.getStringExtra("id")
        } else {
            finish()
            Toast.makeText(
                this@DetailsActivity,
                "some unexpected error occured",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (ConnectionManager().isNetworkAvailable(this@DetailsActivity)) {
            progressLayout.visibility = View.VISIBLE

            val call = request.fetchRestaurantsItems("9bf534118365f1", restaurantId ?: "")
            call.enqueue(object : Callback<Example> {
                override fun onResponse(call: Call<Example>, response: Response<Example>) {
                    progressLayout.visibility = View.GONE
                }

                override fun onFailure(call: Call<Example>, t: Throwable) {
                    progressLayout.visibility = View.GONE
                    Toast.makeText(
                        this@DetailsActivity,
                        "Failed to fetch list Reason: $t",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            /* val queue = Volley.newRequestQueue(this@DetailsActivity)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result/id"
            val jsonparams = JSONObject()
            jsonparams.put("restaurant_id", restaurantId)
            val jsonRequest =
                object : JsonObjectRequest(Request.Method.GET, url, jsonparams, Response.Listener {
                    try {
                        val success = it.get("success") as Boolean
                        if (success) {
                            val restaurantJsonObject = it.getJSONObject("restaurant_data")
                            val restaurantImageUrl = restaurantJsonObject.getString("image")
                            progressLayout.visibility = View.GONE
                            textNo.text = restaurantJsonObject.getString("textNo")
                            textRestro1.text = restaurantJsonObject.getString("Name")
                            textcost.text = restaurantJsonObject.getString("cost_for_Two")


                            val RestaurantEntity = RestaurantEntity(
                                restaurantId,
                                textNo.text.toString(),
                                textRestro1.text.toString(),
                                textcost.text.toString(),
                                restaurantImageUrl
                            )

                            val checkFav = RestaurentsAdapter.DBAsyncTask(
                                applicationContext, RestaurantEntity,
                                1
                            ).execute()
                            val isFav = checkFav.get()
                            if (isFav) {
                                btnadd.text = "Remove"
                                val favColor = ContextCompat.getColor(
                                    applicationContext,
                                    R.color.colorFavourite
                                )
                            } else {
                                btnadd.text = "Add "
                                val noFavColor =
                                    ContextCompat.getColor(applicationContext, R.color.colorPrimary)
                                btnadd.setBackgroundColor(noFavColor)
                            }
                            btnadd.setOnClickListener {
                                if (!RestaurentsAdapter.DBAsyncTask(
                                        applicationContext,
                                        RestaurantEntity,
                                        1
                                    ).execute().get()
                                ) {

                                    val async =
                                        RestaurentsAdapter.DBAsyncTask(
                                            applicationContext,
                                            RestaurantEntity,
                                            2
                                        ).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Restaurant added to favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        btnadd.text = "Remove "
                                        val favColor = ContextCompat.getColor(
                                            applicationContext,
                                            R.color.colorFavourite
                                        )
                                        btnadd.setBackgroundColor(favColor)
                                    } else {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Some error occurred!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val async = RestaurentsAdapter.DBAsyncTask(
                                        applicationContext, RestaurantEntity,
                                        3
                                    ).execute()
                                    val result = async.get()
                                    if (result) {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Book removed from favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        btnadd.text = "Add "
                                        val noFavColor =
                                            ContextCompat.getColor(
                                                applicationContext,
                                                R.color.colorPrimary
                                            )
                                        btnadd.setBackgroundColor(noFavColor)
                                    } else {
                                        Toast.makeText(
                                            this@DetailsActivity,
                                            "Some error occurred!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }

                        } else {
                            Toast.makeText(
                                this@DetailsActivity,
                                "some error occured in details activity",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    } catch (e: Exception) {
                        Toast.makeText(
                            this@DetailsActivity,
                            "some  catch error occured",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                }, Response.ErrorListener {
                    Toast.makeText(this@DetailsActivity, "volley error occured", Toast.LENGTH_SHORT)
                        .show()
                }) {

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9bf534118365f1"
                        return headers
                    }
                }
            queue.add(jsonRequest)*/
        } else {
            val builder = AlertDialog.Builder(this@DetailsActivity)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(this@DetailsActivity)
            }
            builder.create()
            builder.show()
        }

        class DBAsyncTask(context: Context, val restaurantEntity: RestaurantEntity, val mode: Int) :
            AsyncTask<Void, Void, Boolean>() {

            val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

            override fun doInBackground(vararg params: Void?): Boolean {

                /*
                Mode 1 -> Check DB if the book is favourite or not
                Mode 2 -> Save the book into DB as favourite
                Mode 3 -> Remove the favourite book
                */

                when (mode) {

                    1 -> {
                        val res: RestaurantEntity? =
                            db.restaurantDao().getRestaurantById(restaurantEntity.id.toString())
                        db.close()
                        return res != null

                    }

                    2 -> {
                        db.restaurantDao().insertRestaurant(restaurantEntity)
                        db.close()
                        return true
                    }

                    3 -> {
                        db.restaurantDao().deleteRestaurant(restaurantEntity)
                        db.close()
                        return true
                    }
                }

                return false
            }

        }
    }
}