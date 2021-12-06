package com.example.pewpew.api

import retrofit2.Response
import retrofit2.http.GET

interface pewpewApi {

    @GET("/burgers")
    suspend fun getBurgers(

    ):Response<List<ItemModel>>

    @GET("/SideOrder")
    suspend fun getSideOrder(

    ):Response<List<ItemModel>>
}