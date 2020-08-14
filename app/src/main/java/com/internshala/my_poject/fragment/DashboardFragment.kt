package com.internshala.my_poject.fragment

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.my_poject.R
import com.internshala.my_poject.adapter.RestaurentsAdapter
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.model.Restaurant
import com.internshala.my_poject.util.ConnectionManager
import com.internshala.my_poject.util.FETCH_RESTAURANTS_URL
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class DashboardFragment : Fragment() {

//    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var restaurentsAdapter: RestaurentsAdapter
    private var restaurantList = arrayListOf<Restaurant>()
    private lateinit var progressBar: ProgressBar
//    private lateinit var rlLoading: RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById(R.id.progressBar) as ProgressBar
//        rlLoading = view.findViewById(R.id.rlLoading) as RelativeLayout
//        rlLoading.visibility = View.VISIBLE

        setUpRecycler(view)
    }

    private fun setUpRecycler(view: View) {
        val recyclerRestaurant:RecyclerView = view.findViewById(R.id.recyclerRestaurant)
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerRestaurant.layoutManager = mLayoutManager
        recyclerRestaurant.itemAnimator = DefaultItemAnimator()
        restaurentsAdapter = RestaurentsAdapter()
        recyclerRestaurant.adapter = restaurentsAdapter

//        val restaurant = Restaurant(1212,"Test Name","5",1000,"ab")
//                                restaurantList.add(restaurant)
//                                restaurentsAdapter.restaurants=restaurantList

        val queue = Volley.newRequestQueue(activity as Context)
        if (ConnectionManager().isNetworkAvailable(activity as Context)) {
            //get the list of favourites food here from the db and compare with the food item's id.
            val listOfFavourites = GetAllFavAsyncTask(context!!).execute().get()
            /*Create a JSON object request*/
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                FETCH_RESTAURANTS_URL,
                null,
                Response.Listener<JSONObject> {
//                    rlLoading.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    recyclerRestaurant.visibility = View.VISIBLE
                    /*Once response is obtained, parse the JSON accordingly*/
                    try {
                        Log.e("data", restaurantList.toString())
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            val resArray = data.getJSONArray("data")
                            Log.d("data",resArray.toString())

                            for (i in 0 until resArray.length()) {
                                val resObject = resArray.getJSONObject(i)
                                val restaurant = Restaurant(
                                    resObject.getString("id").toInt(),
                                    resObject.getString("name"),
                                    resObject.getString("rating"),
                                    resObject.getString("cost_for_one").toInt(),
                                    resObject.getString("image_url")
                                )
                                restaurantList.add(restaurant)
                            }
                            listOfFavourites?.let {
                                for( i in restaurantList.indices){
                                    for( j in it.indices){
                                        //if Ids match anywhere in the list then add true value in restaurant item
                                    if(restaurantList[i].id == it[j]){
                                        restaurantList[i].isFavourite=true
                                    }
                                    }
                                }
                            }

                            restaurentsAdapter.restaurants = restaurantList

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error: VolleyError? ->
                    Toast.makeText(activity as Context, error?.message, Toast.LENGTH_SHORT).show()
                }) {


                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }

            queue.add(jsonObjectRequest)

        } else {
            val builder = AlertDialog.Builder(activity as Context)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            builder.create()
            builder.show()
        }

    }

//comment always why you are writing it and what is the use of this function,class or variable.
    class GetAllFavAsyncTask(
        context: Context
    ) : AsyncTask<Void, Void, List<Int>>() {
        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java,"res-db").build()
        override fun doInBackground(vararg params: Void?): List<Int> {

            val list = db.restaurantDao().getAllRestaurants()
            val listOfIds = arrayListOf<Int>()
            for (i in list) {
                listOfIds.add(i.id)
            }
            return listOfIds
        }
    }
}

