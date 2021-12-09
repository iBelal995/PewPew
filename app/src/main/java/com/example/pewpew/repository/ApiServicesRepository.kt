package com.example.pewpew.repository

import android.content.Context
import com.example.pewpew.api.pewpewApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


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