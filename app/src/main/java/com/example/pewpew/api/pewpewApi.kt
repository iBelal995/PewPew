package com.example.pewpew.api

import com.example.pewpew.model.CartModel
import com.example.pewpew.model.menumodel.MenuModelItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface pewpewApi {

    @GET("/menu")
    suspend fun getMenu(
    ):Response<List<MenuModelItem>>

    @GET("/menu")
    suspend fun getMenu(
        @Query("type") type:String
    ):Response<List<MenuModelItem>>

    @POST("/cart")
    suspend fun addToCart(
      @Body item:CartModel
    ):Response<CartModel>

    @GET("/cart")
    suspend fun getCart(
        @Query("userid") usreid:String
    ):Response<List<CartModel>>

    @DELETE("/cart/{id}")
    suspend fun removeFromCart(
    @Path("id")  id: String
    ):Response<ResponseBody>

    @PUT("/cart/{id}")
    suspend fun updateCart(
        @Path("id") id:String,
        @Body item:CartModel
    ):Response<CartModel>
}