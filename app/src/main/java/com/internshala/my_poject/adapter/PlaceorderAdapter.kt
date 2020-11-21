package com.internshala.my_poject.adapter

import android.content.Context
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.internshala.my_poject.R
import com.internshala.my_poject.model.Datum
import kotlinx.android.synthetic.main.deatils_itemlist.view.*

class PlaceorderAdapter()  : RecyclerView.Adapter<PlaceorderAdapter.PlaceorderViewHolder>() {

    var Orders: ArrayList<Datum> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceorderAdapter.PlaceorderViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.recycler_dashboard_single_row, parent, false)

        return PlaceorderViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceorderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return Orders.size
    }
      inner class PlaceorderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
          fun bind(position: Int) {
              val restaurant = Orders[position]
             itemView.textCost1.text =  restaurant.costForOne.toString()
              itemView.textRestro1.text  = restaurant.name
          }

          private var small_view: TextView = view.findViewById(R.id.small_view)
          private var textRestro1: TextView = view.findViewById(R.id.textRestro1)
          private var textCost1: TextView = view.findViewById(R.id.textCost1)
        }



}