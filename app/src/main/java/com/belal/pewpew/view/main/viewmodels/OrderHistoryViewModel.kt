package com.belal.pewpew.view.main.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belal.pewpew.model.HistoryModel
import com.belal.pewpew.repository.ApiServicesRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "OrderHistoryViewModel"

class OrderHistoryViewModel : ViewModel() {
    private val apiService = ApiServicesRepository
    val historyLiveData = MutableLiveData<List<HistoryModel>>()
    val historyxlLiveData = MutableLiveData<List<HistoryModel>>()
    val historyErrorLiveData = MutableLiveData<String>()
    val userid: String = FirebaseAuth.getInstance().currentUser?.uid ?: "Error"


    fun callHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.getHistory(userid)
                if (response.isSuccessful) {
                    response.body()?.run {
                        Log.d(TAG, response.body().toString())
                        historyLiveData.postValue(this)
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

    fun getHistoryspec(ordernumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val response = apiService.getHistorySpec(ordernumber)
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