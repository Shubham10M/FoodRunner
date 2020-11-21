package com.internshala.my_poject.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


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

//data class PLACE_ORDER(
//    var user_id : String,
//    var  restaurantId:  String,
//    var  total_cost : Double ,
//    var food : List<Food>
//)
  data class Food(
    @SerializedName("food_item_id") var food_item_id : String
)
data class UserInfo (
    @SerializedName("user_id") var userId: String,
    @SerializedName("restaurantId") var  restaurantId:  String,
    @SerializedName("total_cost")    var  total_cost : Double ,
    @SerializedName("food") var food : List<Food>
)

@Parcelize
data class Datum(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("cost_for_one")
    var costForOne: String? = "0",
    @SerializedName("restaurant_id")
    var restaurantId: String? = null,
    @SerializedName("rating")
    var rating: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null

):Parcelable {
    var isFavourite: Boolean = false
    var isAddedToCart: Boolean = false
}
