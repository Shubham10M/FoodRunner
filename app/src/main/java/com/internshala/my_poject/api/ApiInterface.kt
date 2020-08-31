package com.internshala.my_poject.api

import com.internshala.my_poject.model.Example
import com.internshala.my_poject.model.RegisterRequest
import com.internshala.my_poject.model.UserProfile
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by Rahul Abrol on 24/8/20.
 */

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

//    @FormUrlEncoded
    @POST("$REGISTER$FETCH_RESULT")
    fun registerUser(@Body user: RegisterRequest): Call<UserProfile>

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