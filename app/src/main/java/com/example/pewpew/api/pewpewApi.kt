package com.example.pewpew.api

import com.example.pewpew.model.ItemsModel
import retrofit2.Response
import retrofit2.http.GET

interface pewpewApi {

    @GET("/burgers")
    suspend fun getBurgers(

    ):Response<List<ItemsModel>>

    @GET("/SideOrder")
    suspend fun getSideOrder(

    ):Response<List<ItemsModel>>
}