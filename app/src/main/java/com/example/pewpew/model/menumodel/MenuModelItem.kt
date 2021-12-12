package com.example.pewpew.model.menumodel


import com.google.gson.annotations.SerializedName

data class MenuModelItem(
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("type")
    val type: String,
)