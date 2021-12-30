package com.belal.pewpew.api

import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import com.belal.pewpew.model.menumodel.MenuModelItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface pewpewApi {

    // to get the all menu from the mockApi
    @GET("/menu")
    suspend fun getMenu(
    ): Response<List<MenuModelItem>>

    //to get specific category menu from mockApi
    @GET("/menu")
    suspend fun getMenu(
        @Query("type") type: String
    ): Response<List<MenuModelItem>>

    //to add item from the menu Api to Cart Api
    @POST("/cart")
    suspend fun addToCart(
        @Body item: CartModel
    ): Response<CartModel>

    //to get the cart Api depends on the user id
    @GET("/cart")
    suspend fun getCart(
        @Query("userid") usreid: String
    ): Response<List<CartModel>>

    //to delete an item from the cart Api according to it's id
    @DELETE("/cart/{id}")
    suspend fun removeFromCart(
        @Path("id") id: String
    ): Response<ResponseBody>

    //to make any changes or updates in the cart Api such as changing the quantity
    @PUT("/cart/{id}")
    suspend fun updateCart(
        @Path("id") id: String,
        @Body item: CartModel
    ): Response<CartModel>

    //to add the order from the cart to the history page
    @POST("/history")
    suspend fun addTohistory(
        @Body item: HistoryModel
    ): Response<HistoryModel>

    //to get the history Api according to the user id
    @GET("/history")
    suspend fun getHistory(
        @Query("userid") userid: String
    ): Response<List<HistoryModel>>

    // to get the order history according to the  ordernumber
    @GET("/history")
    suspend fun getHistoryspec(
        @Query("ordernumber") ordernumber: Int
    ): Response<List<HistoryModel>>
}