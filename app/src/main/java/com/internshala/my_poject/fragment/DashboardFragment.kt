package com.internshala.my_poject.fragment

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageInstaller
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.internshala.my_poject.R
import com.internshala.my_poject.adapter.RestaurentsAdapter
import com.internshala.my_poject.api.ApiClient
import com.internshala.my_poject.api.ApiInterface
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
import com.internshala.my_poject.model.Datum
import com.internshala.my_poject.model.Example
import com.internshala.my_poject.util.ConnectionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.recyclerRestaurant
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/*
* 1. git add .
* 2. git commit -m "your message"
* 3. git push -u origin your_branch_name
*
*< For pull not for use its just a heading>
* 4. git pull origin  your_branch_name
* */
class DashboardFragment : Fragment() {

    //    private lateinit var recyclerRestaurant: RecyclerView
    private val restaurentsAdapter by lazy {
        RestaurentsAdapter()
    }
//    private var restaurantList = arrayListOf<Restaurant>()

    //    private lateinit var rlLoading: RelativeLayout
    private val request = ApiClient.buildService(ApiInterface::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler(view)
    }

    private fun setUpRecycler(view: View) {
        recyclerRestaurant?.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            recyclerRestaurant.adapter = restaurentsAdapter
        }
        // show progress
        view.progressBar.visibility = View.VISIBLE

        if (ConnectionManager().isNetworkAvailable(activity as Context)) {
            //get the list of favourites food here from the db and compare with the food item's id.
            val listOfFavourites = GetAllFavAsyncTask(context!!).execute().get()

            val call = request.fetchRestaurants("9bf534118365f1")
            call.enqueue(object : Callback<Example> {
                override fun onResponse(call: Call<Example>, response: Response<Example>) {

                    view.progressBar.visibility = View.GONE
                    view. recyclerRestaurant.visibility = View.VISIBLE

                    if (response.isSuccessful) {
                        response.body()?.data?.data?.let {
                            listOfFavourites?.let { fabList ->
                                for (i in it.indices) {
                                    for (j in fabList.indices) {
                                        //if Ids match anywhere in the list then add true value in restaurant item
                                        if (it[i].id == fabList[j].toString()) {
                                            it[i].isFavourite = true
                                        }
                                    }
                                }

                            }
                            restaurentsAdapter.restaurants = it as ArrayList<Datum>
                        }

                }

                }

                override fun onFailure(call: Call<Example>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch list Reason: $t",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })



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

    class GetAllFavAsyncTask(
        context: Context
    ) : AsyncTask<Void, Void, List<Int>>() {
        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()
        override fun doInBackground(vararg params: Void?): List<Int> {

            val list = db.restaurantDao().getAllRestaurants()
            val listOfIds = arrayListOf<Int>()
            for (i in list) {
                listOfIds.add(i.id)
            }
            return listOfIds
        }
    }

    // this is code is  to add the favourite restaurant in a favourite fragment
    class DBAsyncTask(context: Context, val restaurantEntity: RestaurantEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {

            /*
            Mode 1 -> Check DB if the restaurant is favourite or not
            Mode 2 -> Save the restaurant into DB as favourite
            Mode 3 -> Remove the favourite restaurant
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



