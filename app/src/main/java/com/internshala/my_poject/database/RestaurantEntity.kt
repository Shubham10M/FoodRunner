package com.internshala.my_poject.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Restaurant")
 data class RestaurantEntity (
    @PrimaryKey val id : Int,
    @ColumnInfo(name ="name") val name: String,
    @ColumnInfo(name = "rating") val rating: String,
    @ColumnInfo(name = "cost_for_Two") val cost_for_Two: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)
