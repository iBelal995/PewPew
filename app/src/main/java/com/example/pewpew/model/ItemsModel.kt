package com.example.pewpew.model


import com.google.gson.annotations.SerializedName

data class ItemsModel(
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int
)