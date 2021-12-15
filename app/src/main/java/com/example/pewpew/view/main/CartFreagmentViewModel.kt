package com.example.pewpew.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pewpew.model.CartModel
import com.example.pewpew.repository.ApiServicesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "CartViewModel"

class CartFreagmentViewModel:ViewModel() {
    val CartLiveData = MutableLiveData<List<CartModel>>()
    val CartLiveDataS = MutableLiveData<String>()
    val CartErrorLiveData = MutableLiveData<String>()
    private val apiService = ApiServicesRepository.get()
    val userid:String = FirebaseAuth.getInstance().currentUser?.uid ?: "Error"
    fun getCart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.getCart(userid)
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                        CartLiveData.postValue(this)
                    }
                } else {
                    Log.d(TAG, response.message())
                    CartErrorLiveData.postValue(response.message())

                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                CartErrorLiveData.postValue(e.message.toString())
            }
        }
    }
    fun removeFromCart(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.removeFromCart(id)
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                        CartLiveDataS.postValue("response successful")
                        getCart()
                    }
                } else {
                    Log.d(TAG, response.message())
                    CartErrorLiveData.postValue(response.message())

                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                CartErrorLiveData.postValue(e.message.toString())
            }
        }
    }
    fun updateCart(item: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.updateCart(item.id,item)
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                            getCart()
                    }
                } else {
                    Log.d(TAG, response.message())
                    CartErrorLiveData.postValue(response.message())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                CartErrorLiveData.postValue(e.message.toString())
            }
        }
    }

}