package com.internshala.my_poject.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.my_poject.R
import com.internshala.my_poject.adapter.RestaurentsAdapter
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
import com.internshala.my_poject.model.Restaurant


class FavouritesFragment : Fragment() {
    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var allRestaurantsAdapter:RestaurentsAdapter
    private var restaurantList = arrayListOf<Restaurant>()
    private lateinit var rlLoading: RelativeLayout
    private lateinit var rlFav: RelativeLayout
    private lateinit var rlNoFav: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        rlFav = view.findViewById(R.id.rlFavorites)
        rlNoFav = view.findViewById(R.id.rlNoFavorites)
        rlLoading = view.findViewById(R.id.rlLoading)
        recyclerRestaurant = view.findViewById(R.id.recyclerRestaurants)
        val backgroundList = FavouritesAsync(activity as Context).execute().get()




        return view
    }
    class FavouritesAsync(context: Context) : AsyncTask<Void, Void, List<RestaurantEntity>>() {
        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): List<RestaurantEntity> {
            return db.restaurantDao().getAllRestaurants()
        }


    }


}