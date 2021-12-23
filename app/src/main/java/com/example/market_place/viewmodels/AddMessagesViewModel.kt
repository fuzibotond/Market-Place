package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.AddMessageRequest
import com.example.market_place.model.AddOrderRequest
import com.example.market_place.model.OrderHelper
import com.example.market_place.repository.Repository

class AddMessagesViewModel(val context: Context, val repository: Repository) : ViewModel() {

    suspend fun addMessageToOrder(order_id :String, message:String) {
        val request = AddMessageRequest(message)
        try {
            val result = repository.addMessageToOrder(MarketPlaceApplication.token,order_id, request )
            Log.d("xxx", "AddMessagesViewModel - add message order response: ${result} ")

        } catch (e: Exception) {
            Log.d("xxx", "AddMessagesViewModel - add message order exception: ${e.toString()}")
        }
    }
    suspend fun addMessageToProduct(product_id :String, message:String) {
        val request = AddMessageRequest(message)
        try {
            val result = repository.addMessageToProduct(MarketPlaceApplication.token,product_id, request)
            Log.d("xxx", "AddMessagesViewModel - add message product  response: ${result} ")
        } catch (e: Exception) {
            Log.d("xxx", "AddMessagesViewModel - add message product exception: ${e.toString()}")

        }
    }
}