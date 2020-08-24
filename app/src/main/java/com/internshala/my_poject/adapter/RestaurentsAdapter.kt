package com.internshala.my_poject.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.internshala.my_poject.R
import com.internshala.my_poject.activity.DetailsActivity
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
import com.internshala.my_poject.model.Datum
import com.squareup.picasso.Picasso

class RestaurentsAdapter() : RecyclerView.Adapter<RestaurentsAdapter.DashboardViewHolder>() {
    var restaurants: ArrayList<Datum> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_dashboard_single_row, parent, false)

        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.bind(position)


    }


    inner class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var textRestro: TextView = view.findViewById(R.id.textRestro)
        private var imgisfav: ImageView = view.findViewById(R.id.imgIsFav)
        private var textCost: TextView = view.findViewById(R.id.textCost)
        private var textRating: TextView = view.findViewById(R.id.textRating)
        private var imgRestaurent: ImageView = view.findViewById(R.id.imgRestaurent)
        private var cardRestaurant: CardView = view.findViewById(R.id.cardRestaurant)

        init {
            imgisfav.setOnClickListener {
                restaurants[adapterPosition].isFavourite =
                    !restaurants[adapterPosition].isFavourite

                //notify adapter after mark as favourite or not.
                notifyDataSetChanged()
            }

            cardRestaurant.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra("id", restaurants.get(adapterPosition).id)
                context.startActivity(intent)
            }
        }

        fun bind(position: Int) {
            val restaurant = restaurants[position]
            textRestro.text = restaurant.name
            textCost.text = restaurant.costForOne.toString()
            textRating.text = restaurant.rating
            Picasso.get().load(restaurant.imageUrl).error(R.drawable.food).into(imgRestaurent)
            if (restaurant.isFavourite) {
                imgisfav.setImageResource(R.drawable.ic_fav_filled)
            } else {
                imgisfav.setImageResource(R.drawable.ic_fav_one)
            }
        }
    }

    //   these should not be here use a callback and add this code to fragment
    class DBAsyncTask(context: Context, val restaurantEntity: RestaurantEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            /*   Mode 1 -> Check DB if the book is favourite or not
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

