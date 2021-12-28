package com.belal.pewpew.repository

import android.content.Context
import com.belal.pewpew.api.pewpewApi
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*


private const val TAG = "ApiServicesRepository"
private const val BASE_URL = "https://61adff11a7c7f3001786f535.mockapi.io/"
class ApiServicesRepository(context: Context) {
    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
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