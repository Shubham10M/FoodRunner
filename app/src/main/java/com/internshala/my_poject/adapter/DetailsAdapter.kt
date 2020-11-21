package com.internshala.my_poject.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.gms.actions.ItemListIntents
import com.internshala.my_poject.R
import com.internshala.my_poject.activity.PlaceOrderActivity
import com.internshala.my_poject.database.RestaurantDatabase
import com.internshala.my_poject.database.RestaurantEntity
import com.internshala.my_poject.model.Datum
import kotlinx.android.synthetic.main.deatils_itemlist.view.*

class DetailsAdapter(val showHideCartButtonClick: () -> Unit) :  RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {
    var Itemslists: ArrayList<Datum> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.deatils_itemlist, parent, false)

        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsAdapter.DetailsViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return Itemslists.size
    }
         inner class DetailsViewHolder(val view : View)  : RecyclerView.ViewHolder(view) {

             init {
                 view.btnadd.setOnClickListener{
                     Itemslists[bindingAdapterPosition].isAddedToCart = !Itemslists[bindingAdapterPosition].isAddedToCart
                     showHideCartButtonClick.invoke()
                     Toast.makeText(view.context,"${ Itemslists[bindingAdapterPosition].isAddedToCart}", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                 }

             }

             fun bind(position: Int) {
                 val restaurant = Itemslists[position]
                 val serial = position+1
                 view.textNo.text = "$serial"
                 view.textRestro1.text = restaurant.name
                 view.textCost1.text = restaurant.costForOne.toString()
   //              Picasso.get().load(restaurant.imageUrl).error(R.drawable.food).into(imgRestaurent)
                 if (restaurant.isAddedToCart) {
                     view.btnadd.text = "Remove"
                     view.btnadd.setBackgroundColor( ContextCompat.getColor(view.context,R.color.colorFavourite))
                 } else {
                     view.btnadd.text = "Add"
//                     view.btnadd.setBackgroundColor( ContextCompat.getColor(view.context,R.color.colorPrimary)))
                 }

             }
    }
    class DBAsyncTask(context: Context, val restaurantEntity: RestaurantEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "res-db").build()

        override fun doInBackground(vararg params: Void?): Boolean {


            /*   Mode 1 -> Check DB if the restaurant is favourite or not
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