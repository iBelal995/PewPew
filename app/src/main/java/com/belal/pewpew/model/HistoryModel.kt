package com.belal.pewpew.model


import com.google.gson.annotations.SerializedName
import java.util.*
//this is the history component needed
data class HistoryModel(
    @SerializedName("count")
    val count: Int,
    @SerializedName("date")
    val date: Date,
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("originalprice")
    val originalprice: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("userid")
    val userid: String,
    @SerializedName("ordernumber")
    val ordernumber: Int,
    @SerializedName("totalprice")
    val totalprice: Double
)