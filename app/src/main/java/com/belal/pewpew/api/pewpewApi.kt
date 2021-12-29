package com.belal.pewpew.api

import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import com.belal.pewpew.model.menumodel.MenuModelItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface pewpewApi {
    /***
     * REQUEST METHOD
    Every method must have an HTTP annotation that provides the request method and relative URL.
    There are eight built-in annotations: HTTP, GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD.
    The relative URL of the resource is specified in the annotation.
     * */


    /** Authorization
     * As a security measure,
     * most API access points require users to provide an authentication
     * token that can be used to verify the identity of the user making the request so as to grant
     * them access to data/ resources from the backend. The client app usually fetches the token upon successful login
     * or registration then saves the token locally and appends it to subsequent requests
     * so that the server can authenticate the user.
     * */
    /***
     * Query parameters are added with the @Query annotation on a method parameter.
     * They are automatically added at the end of the URL.
     * */

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

    @POST("/history")
    suspend fun addTohistory(
        @Body item:HistoryModel
    ):Response<HistoryModel>

    @GET("/history")
    suspend fun getHistory(
        @Query("userid") userid:String
    ):Response<List<HistoryModel>>

    @GET("/history")
    suspend fun getHistoryspec(
        @Query("ordernumber") ordernumber:Int
    ):Response<List<HistoryModel>>
}