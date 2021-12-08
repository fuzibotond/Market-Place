package com.example.market_place.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.Order
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch


class ListOrderViewModel(private val repository: Repository) : ViewModel() {
    var orders = MutableLiveData<List<Order>>()

    init{
        Log.d("xxx", "ListViewModel constructor - Token: ${MarketPlaceApplication.token}")
        getOrders()
    }
    fun getOrders() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getOrders(MarketPlaceApplication.token)
                orders.value = result.orders
                Log.d("xxx", "ListOrdersViewModel - #products:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "ListOrderViewModel exception: ${e.toString()}")
            }
        }
    }
}