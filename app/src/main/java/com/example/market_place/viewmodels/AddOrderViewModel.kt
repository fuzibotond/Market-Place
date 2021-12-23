package com.example.market_place.viewmodels


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.*
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch
import java.security.cert.TrustAnchor


class AddOrderViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var order = MutableLiveData<OrderHelper>()
    init {
        order.value = OrderHelper()
    }
    suspend fun addOrder() {
        val request = AddOrderRequest(
            order.value?.uploadImages,
            order.value?.title,
            order.value?.description,
            order.value?.price_per_unit,
            order.value?.units,
            order.value?.owner_username
        )
        try {
            val result = repository.addOrder(MarketPlaceApplication.token, request)
            Log.d("xxx", "addOrder - add order response: ${result} ")

        } catch (e: Exception) {
            Log.d("xxx", "addOrder - add order exception: ${e.toString()}")
        }
    }
    suspend fun removeOrder(order_id: String) {
        try {
            val result = repository.removeOrder(MarketPlaceApplication.token,order_id)
            Log.d("xxx", "addOrder - remove order response:  ${result.message}")
        } catch (e: Exception) {
            Log.d("xxx", "addOrder - remove order exception: ${e.toString()}")
        }
    }

}
