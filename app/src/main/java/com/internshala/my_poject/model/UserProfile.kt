package com.internshala.my_poject.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Rahul Abrol on 31/8/20.
 */
data class UserProfile(
    @SerializedName("data")
    var data: UserData
)

data class UserData(
    @SerializedName("success")
    var success: String? = null,
    @SerializedName("data")
    var user: User? = null
)

data class User(
    @SerializedName("user_id")
    var userId: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("mobile_number")
    var mobile: String? = null,
    @SerializedName("address")
    var address: String? = null
)