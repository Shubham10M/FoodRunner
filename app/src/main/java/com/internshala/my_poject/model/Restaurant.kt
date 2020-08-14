package com.internshala.my_poject.model

data class  Restaurant(
    val id : Int,
    val Name : String,
    val rating: String,
    val costForTwo : Int,
    val image : String,
var isFavourite:Boolean=false
)
