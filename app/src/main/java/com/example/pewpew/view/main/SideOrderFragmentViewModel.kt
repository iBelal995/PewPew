package com.example.pewpew.view.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pewpew.model.CartModel
import com.example.pewpew.model.menumodel.MenuModelItem
import com.example.pewpew.repository.ApiServicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


private const val TAG = "SideOrderFragment"
class SideOrderFragmentViewModel: ViewModel() {
    private val apiService = ApiServicesRepository.get()

    val menuLiveData = MutableLiveData<List<MenuModelItem>>()
    val menuErrorLiveData = MutableLiveData<String>()
    val CartLiveData = MutableLiveData<CartModel>()
    val CartErrorLiveData = MutableLiveData<String>()
    var selectedItemId = MutableLiveData<MenuModelItem>()
    fun callMenu(){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.getMenu("sideorder")
                if(response.isSuccessful){
                    response.body()?.run {
                        Log.d(TAG,response.body().toString())
                        menuLiveData.postValue(this)
                    }
                }else{
                    Log.d(TAG,response.message())
                    menuErrorLiveData.postValue(response.message())

                }

            }catch (e: Exception){
                Log.d(TAG, e.message.toString())
                menuErrorLiveData.postValue(e.message.toString())
            }
        }
    }
    fun addToCart(item: CartModel){
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.addToCart(item)
                if(response.isSuccessful){
                    response.body()?.run {
                        Log.d(TAG,response.body().toString())
                        CartLiveData.postValue(this)
                    }
                }else{
                    Log.d(TAG,response.message())
                    CartErrorLiveData.postValue(response.message())

                }

            }catch (e: Exception){
                Log.d(TAG, e.message.toString())
                CartErrorLiveData.postValue(e.message.toString())
            }
        }
    }
}