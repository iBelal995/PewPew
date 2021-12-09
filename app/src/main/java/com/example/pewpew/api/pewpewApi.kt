package com.example.pewpew.api

import com.example.pewpew.model.menumodel.MenuModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface pewpewApi {

    @GET("/menu")
    suspend fun getMenu(
    ):Response<List<MenuModelItem>>

    @GET("/menu")
    suspend fun getMenu(
        @Query("type") type:String
    ):Response<List<MenuModelItem>>
}