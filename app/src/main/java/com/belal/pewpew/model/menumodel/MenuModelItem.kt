package com.belal.pewpew.model.menumodel


import com.google.gson.annotations.SerializedName
/**
 * Serializers: A serializer allows to convert a Json string to corresponding Kotlin Model or objects.
 * */
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