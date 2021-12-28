package com.belal.pewpew.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class CartModel(
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    var price: Int,
    @SerializedName("userid")
    val userid: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("date")
    var date: Date = Date()
)