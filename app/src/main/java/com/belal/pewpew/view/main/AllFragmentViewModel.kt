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

class AllFragmentViewModel: ViewModel() {
    private val apiService = ApiServicesRepository.get()
    /**
     * The ViewModel is a class whose role is to provide data to the UI and survive configuration changes.
     * A ViewModel acts as a communication center between the Repository and the UI.
     * You can also use a ViewModel to share data between fragments.
     *
     * A ViewModel holds your app's UI data in a lifecycle-conscious way that survives configuration changes.
     * Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
     * Your activities and fragments are responsible for drawing data to the screen,
     * while your ViewModel is responsible for holding and processing all the data needed for the UI.
     * */
    val menuLiveData = MutableLiveData<List<MenuModelItem>>()
    val menuErrorLiveData = MutableLiveData<String>()
    val CartLiveData = MutableLiveData<CartModel>()
    val CartErrorLiveData = MutableLiveData<String>()
    var selectedItemId = MutableLiveData<MenuModelItem>()
    fun callMenu(){
        /***
         * Scope in Kotlin’s coroutines can be defined as the restrictions within which the Kotlin coroutines are being executed
         * Scopes help to predict the lifecycle of the coroutines. There are basically 4 scopes in Kotlin coroutines:
         * */

        /***
         * 1. Global Scope
        Global Scope is one of the ways by which coroutines are launched.
        When Coroutines are launched within the global scope, they live long as the application does.
        If the coroutines finish it’s a job, it will be destroyed and will not keep alive until the application dies
         * */

        /**
         * 2. LifeCycle Scope
        The lifecycle scope is the same as the global scope,
        but the only difference is that when we use the lifecycle scope,
        all the coroutines launched within the activity also dies when the activity dies.
         * */

        /***
         * 3. ViewModel Scope
        It is also the same as the lifecycle scope,
        only difference is that the coroutine in this scope will live as long the view model is alive.
         * */

        /***
         * 4. Coroutine Scope
         *  It creates a new scope and does not complete until all children’s coroutines complete.
         *  So we are creating a scope, we are running coroutines and inside the scope,
         *  we can create other coroutines.
         *  This coroutine that starts here does not complete until all the inner coroutines complete as well.
         * */
        viewModelScope.launch(Dispatchers.IO) {
            // Coroutines Dispatchers
            /**
             * Kotlin coroutines use dispatchers to determine which threads are used for coroutine execution.
             * To run code outside of the main thread, you can tell Kotlin coroutines to perform work on either the Default or IO dispatcher.
             * In Kotlin, all coroutines must run in a dispatcher, even when they're running on the main thread.
             * Coroutines can suspend themselves, and the dispatcher is responsible for resuming them.
             * */

            // To specify where the coroutines should run, Kotlin provides three dispatchers that you can use:

            /**
             * Dispatchers.Main -
             * Use this dispatcher to run a coroutine on the main Android thread.
             * This should be used only for interacting with the UI and performing quick work.
             * */

            /***
             * Dispatchers.IO -
             * This dispatcher is optimized to perform disk or network I/O outside of the main thread.
             * Examples include using the Room component, reading from or writing to files,
             * and running any network operations.
             * */

            /**
             * Dispatchers.Default -
             * This dispatcher is optimized to perform CPU-intensive work outside of the main thread.
             * */
            try {

                val response = apiService.getMenu()
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
        }}
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