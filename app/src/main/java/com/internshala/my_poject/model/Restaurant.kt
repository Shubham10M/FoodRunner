package com.internshala.my_poject.model

import com.google.gson.annotations.SerializedName


data class Restaurant(
    val id: Int,
    val Name: String,
    val rating: String,
    val costForTwo: Int,
    val image: String,
    var isFavourite: Boolean = false
)

data class Example(
    @SerializedName("data")
    var data: Data
)

data class Data(
    @SerializedName("success")
    var success: String? = null,
    @SerializedName("data")
    var data: List<Datum>? = null
)

class Datum(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cost_for_one")
    var costForOne: String? = null,
    @SerializedName("restaurant_id")
    var restaurantId: String? = null,
    @SerializedName("rating")
    var rating: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null
) {
    var isFavourite: Boolean = false
}
