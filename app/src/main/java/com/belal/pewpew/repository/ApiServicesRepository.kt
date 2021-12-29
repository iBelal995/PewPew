package com.belal.pewpew.repository

import android.content.Context
import com.belal.pewpew.api.pewpewApi
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*

/**
 * A Repository is a class that abstracts access to multiple data sources (Room db, Network).
 * It is a suggested best practice for code separation and architecture. A Repository class handles data operations
 * */
/***
 *
 * To work with Retrofit you basically need the following three classes:
Model class which is used as a JSON model
Interfaces that define the possible HTTP operations
Retrofit.Builder class - Instance which uses the interface and the Builder API to allow defining the URL end point for the HTTP operations.
 * */

private const val TAG = "ApiServicesRepository"
private const val BASE_URL = "https://61adff11a7c7f3001786f535.mockapi.io/"
class ApiServicesRepository(context: Context) {
    // Retrofit.Builder
    // And we need to specify a factory for deserializing the response using the Gson library
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    //  Builder API
    /**
     * You can then use the create method from the retrofitService to get an instance of the api.
     * */
    private val retrofitApi = retrofitService.create(pewpewApi::class.java)

    suspend fun getMenu() = retrofitApi.getMenu()
    suspend fun getMenu(type:String) = retrofitApi.getMenu(type)
    suspend fun getCart(userid:String) = retrofitApi.getCart(userid)
    suspend fun addToCart(item: CartModel) = retrofitApi.addToCart(item)
    suspend fun removeFromCart(id:String) = retrofitApi.removeFromCart(id)
    suspend fun updateCart(id:String,item:CartModel) = retrofitApi.updateCart(id,item)
    suspend fun addToHistory(item:HistoryModel) = retrofitApi.addTohistory(item)
    suspend fun getHistory(userid: String) = retrofitApi.getHistory(userid)
    suspend fun getHistorySpec(ordernumber: Int) = retrofitApi.getHistoryspec(ordernumber)

    /***
     * this companion object for restricts the instantiation of a class to one "single" instance.
     * This is useful when exactly one object is needed to coordinate actions across the system.
     * */
    companion object{

        private var instance: ApiServicesRepository? = null

        fun init(context: Context){
            if (instance == null)
                instance = ApiServicesRepository(context)
        }

        fun get(): ApiServicesRepository{
            return instance?: throw Exception("ApiServicesRepository must be initialized")
        }
    }
}