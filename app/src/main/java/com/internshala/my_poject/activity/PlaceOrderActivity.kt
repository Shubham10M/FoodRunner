package com.internshala.my_poject.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.internshala.my_poject.R
import com.internshala.my_poject.adapter.DetailsAdapter
import com.internshala.my_poject.adapter.PlaceorderAdapter
import com.internshala.my_poject.api.ApiClient
import com.internshala.my_poject.api.ApiInterface
import com.internshala.my_poject.model.Datum
import com.internshala.my_poject.model.Example
import com.internshala.my_poject.model.Food
import com.internshala.my_poject.model.UserInfo
import com.internshala.my_poject.util.ConnectionManager
import com.internshala.my_poject.util.PLACE_ORDER
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceOrderActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var ordering_from: TextView
    lateinit var total_rs: Button
    var totalPrice = 0
    private val PlaceorderAdapter by lazy {
        PlaceorderAdapter()
//            handleVisibility()
//
//        }
    }


    val list = arrayListOf<Food>()
    private val request = ApiClient.buildService(ApiInterface::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

       val foodList =intent.getParcelableArrayListExtra<Datum>("foodIdArray")

        if (foodList!=null && foodList.isNotEmpty()) {
            for (i in foodList.indices) {
                list.add(Food(foodList[i].id!!))
                totalPrice.plus(foodList[i].costForOne!!.toInt())
            }
        }
        Log.e("total price value" ,"----------------------> "+totalPrice)

        ordering_from = findViewById(R.id.ordering_from)
        total_rs = findViewById(R.id.total_rs)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "My Cart"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        total_rs.text = "Place Order (Total:Rs. $totalPrice)"
        ItemsView.apply {
            layoutManager = LinearLayoutManager(this@PlaceOrderActivity)
            adapter = PlaceorderAdapter
        }


        total_rs.setOnClickListener {
            var resId = intent.getStringExtra("resId")
            if (ConnectionManager().isNetworkAvailable(this)) {
                val userInfo = UserInfo("99", resId,totalPrice.toDouble(),list)
                val call = request.addUser("9bf534118365f1",userInfo)
                call.enqueue(object :Callback<Any>{
                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                        ItemsView.visibility = View.VISIBLE
                            Log.e("Place Order Response","-------------------------------$it" )
                            startActivity(Intent(this@PlaceOrderActivity, ConfirmActivity::class.java))
//                                DetailsAdapter.Itemslists = it as ArrayList<Datum>
                    }

                    override fun onFailure(call: Call<Any>, t: Throwable) {
                        Log.e("Place Order Fail","-------------------------------")

                    }

                })
            }
        }

    }
}