package com.internshala.my_poject.api

import com.internshala.my_poject.model.Example
<<<<<<< HEAD
import com.internshala.my_poject.model.UserInfo
import com.internshala.my_poject.util.PLACE_ORDER
=======
import com.internshala.my_poject.model.RegisterRequest
import com.internshala.my_poject.model.UserProfile
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
<<<<<<< HEAD
import java.util.*

=======
>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a


const val RESTAURANTS = "restaurants/"
const val REGISTER = "register/"
const val FETCH_RESULT = "fetch_result/"

interface ApiInterface {

    @GET("$RESTAURANTS$FETCH_RESULT{restaurant_id}/")
    fun fetchRestaurantsItems(
        @Header("token") token: String,
        @Path("restaurant_id") id: String
    ): Call<Example>

    @GET("$RESTAURANTS$FETCH_RESULT")
    fun fetchRestaurants(
        @Header("token") token: String
    ): Call<Example>

<<<<<<< HEAD
    @POST("$RESTAURANTS$PLACE_ORDER")
    fun addUser(@Header("token") token: String ="9bf534118365f1",
        @Body userData: UserInfo): Call<Any>
=======
//    @FormUrlEncoded
    @POST("$REGISTER$FETCH_RESULT")
    fun registerUser(@Body user: RegisterRequest): Call<UserProfile>

>>>>>>> 8a1e3206c2aff05ad4a5fe10d365a9bfe67c761a
}


object ApiClient {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://13.235.250.119/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}