package com.belal.pewpew.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belal.pewpew.model.CartModel
import com.belal.pewpew.model.menumodel.MenuModelItem
import com.belal.pewpew.repository.ApiServicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

private const val TAG = "AllViewModel"

class AllFragmentViewModel : ViewModel() {
    private val apiService = ApiServicesRepository.get()

    /**
     * The ViewModel is a class whose role is to provide data to the UI and survive configuration changes.
     * A ViewModel acts as a communication center between the Repository and the UI.
     * A ViewModel holds UI data in a lifecycle-conscious way that survives configuration changes.
     * ViewModel is responsible for holding and processing all the data needed for the UI.*/
    val menuLiveData = MutableLiveData<List<MenuModelItem>>()
    val menuErrorLiveData = MutableLiveData<String>()
    val CartLiveData = MutableLiveData<CartModel>()
    val CartErrorLiveData = MutableLiveData<String>()

    //to call the menu from the Api
    fun callMenu() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.getMenu()
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                        menuLiveData.postValue(this)
                    }
                } else {
                    Log.d(TAG, response.message())
                    menuErrorLiveData.postValue(response.message())

                }

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
                menuErrorLiveData.postValue("Please make sure you are connected to the internet")
            }
        }
    }

    //to add the items to the cart
    fun addToCart(item: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.addToCart(item)
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
}