package com.example.market_place.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.Message
import com.example.market_place.model.Order
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch

class ListMessagesViewModel (private val repository: Repository) : ViewModel() {
    var orderMessageList = MutableLiveData<List<Message>>()
    var item_count_order = MutableLiveData<Int>()
    var productMessageList = MutableLiveData<List<Message>>()
    var item_count_product = MutableLiveData<Int>()
    fun getMessageToOrders(order_id:String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.getMessagesForOrder(MarketPlaceApplication.token,order_id )
                orderMessageList.value = result.messages
                item_count_order.value = result.item_count
                Log.d("xxx", "ListMessagesViewModel - order messages:  ${result.messages}")
            }catch(e: Exception){
                Log.d("xxx", "ListMessagesViewModel order messages exception: ${e.toString()}")
            }
        }
    }
    fun getMessageToProduct(product_id:String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.getMessagesForProduct(MarketPlaceApplication.token,product_id )
                productMessageList.value = result.messages
                item_count_product.value = result.item_count
                Log.d("xxx", "ListMessagesViewModel - product messages:  ${result.messages}")
            }catch(e: Exception){
                Log.d("xxx", "ListMessagesViewModel product messages exception: ${e.toString()}")
            }
        }
    }
}