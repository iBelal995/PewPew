package com.belal.pewpew.view.main.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.HistoryModel
import com.belal.pewpew.repository.ApiServicesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "CartViewModel"

class CartFreagmentViewModel : ViewModel() {
    val CartLiveData = MutableLiveData<List<CartModel>>()
    val CartLiveDataS = MutableLiveData<String>()
    val CartErrorLiveData = MutableLiveData<String>()
    val historyxlLiveData = MutableLiveData<HistoryModel>()
    val historyErrorLiveData = MutableLiveData<String>()
    private val apiService = ApiServicesRepository
    val userid: String = FirebaseAuth.getInstance().currentUser?.uid ?: "Error"
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
                CartErrorLiveData.postValue("Please make sure you are connected to the internet")
            }
        }
    }

    //to remove an item from the cart
    fun removeFromCart(id: String) {
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
                CartErrorLiveData.postValue("Please make sure you are connected to the internet")
            }
        }
    }

    // to be able to change the quantity from the cart page
    fun updateCart(item: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.updateCart(item.id, item)
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
                CartErrorLiveData.postValue("Please make sure you are connected to the internet")
            }
        }
    }

    // to add the current cart list to the order history list
    fun addToHistory(item: HistoryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.addToHistory(item)
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                        historyxlLiveData.postValue(this)
                    }
                } else {
                    Log.d(TAG, response.message())
                    historyErrorLiveData.postValue(response.message())

                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                historyErrorLiveData.postValue("Please make sure you are connected to the internet")
            }
        }
    }
}