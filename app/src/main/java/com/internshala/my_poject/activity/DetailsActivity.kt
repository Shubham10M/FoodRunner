package com.internshala.my_poject.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
<<<<<<< HEAD
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.internshala.my_poject.R
import com.internshala.my_poject.adapter.DetailsAdapter
import com.internshala.my_poject.api.ApiClient
import com.internshala.my_poject.api.ApiInterface
import com.internshala.my_poject.fragment.DashboardFragment
import com.internshala.my_poject.model.Datum
=======
import androidx.room.Room
import com.internshala.my_poject.R
import com.internshala.my_poject.base.BaseActivity
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a
import com.internshala.my_poject.model.Example
import com.internshala.my_poject.util.ConnectionManager
import com.internshala.my_poject.util.Utils
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : BaseActivity() {

    var restaurantId: String? = null
<<<<<<< HEAD
    private val DetailsAdapter by lazy {
        DetailsAdapter(){
           handleVisibility()

        }
    }

    private fun handleVisibility() {
        btnaddTocart.visibility = View.GONE
        for (i in DetailsAdapter.Itemslists.indices){
//            if there is single item available in cart mark the visibility of
//            the button to visible and break the loop
            if (DetailsAdapter.Itemslists[i].isAddedToCart){
                btnaddTocart.visibility = View.VISIBLE
                break
            }
        }
    }

    private val request = ApiClient.buildService(ApiInterface::class.java)
=======
//    private val request = ApiClient.buildService(ApiInterface::class.java)
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

//        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Restaurant Details"

        ItemsView.apply {
            layoutManager =LinearLayoutManager(this@DetailsActivity)
            adapter = DetailsAdapter
        }

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
          val items = arrayListOf<Datum>()

            for (i in DetailsAdapter.Itemslists.indices){
//            if there is single item available in cart mark the visibility of
//            the button to visible and break the loop
                if (DetailsAdapter.Itemslists[i].isAddedToCart){
                    items.add(DetailsAdapter.Itemslists[i])
                }
            }
            val intent = Intent(this@DetailsActivity, PlaceOrderActivity::class.java)
            intent.putExtra("foodIdArray", items)
            intent.putExtra("resId", DetailsAdapter.Itemslists[0].restaurantId)

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
//            progressLayout.visibility = View.VISIBLE
                val listOfFavourites = DashboardFragment.GetAllFavAsyncTask(this@DetailsActivity).execute().get()

                val call = request.fetchRestaurantsItems("9bf534118365f1", restaurantId!!)
                call.enqueue(object : Callback<Example> {
                    override fun onResponse(call: Call<Example>, response: Response<Example>) {
                        // progressLayout.visibility = View.GONE
                        ItemsView.visibility = View.VISIBLE
                        if (response.isSuccessful) {
                            response.body()?.data?.data?.let {
                                Log.e("detailacti" , "-------------------------------------------------- $it")

                                DetailsAdapter.Itemslists = it as ArrayList<Datum>
                            }
                        }
                    }

                    override fun onFailure(call: Call<Example>, t: Throwable) {
                        //  progressLayout.visibility = View.GONE
                        Toast.makeText(
                            this@DetailsActivity,
                            "Failed to fetch list Reason: $t",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })


            } else {
                val builder = AlertDialog.Builder(this@DetailsActivity)
                builder.setTitle("Error")
                builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
                builder.setCancelable(false)
                builder.setPositiveButton("Ok") { _, _ ->
                    ActivityCompat.finishAffinity(this@DetailsActivity)
                }
<<<<<<< HEAD
                builder.create()
                builder.show()
            }
=======
            queue.add(jsonRequest)*/
        } else {
            Utils.noInternetDialog(this, { _, _ ->
                finish()
            }, { _, _ ->

            })
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a
        }

    }



