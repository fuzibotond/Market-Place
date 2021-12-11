package com.example.market_place.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.model.Order
import com.example.market_place.model.Product

class SharedViewModel : ViewModel() {
    val detailsProduct = MutableLiveData<Product>()
    val myMarketProducts = MutableLiveData<List<Product>>()
    val searchingKeyword = MutableLiveData<String>()
    val orders = MutableLiveData<List<Order>>()
    val order_item_count = MutableLiveData<Int>()
    val savedProductToAdd = MutableLiveData<Product>()
    val orderToAdd = MutableLiveData<Order>()

    fun saveDetailsProduct(product:Product){
        detailsProduct.value = product
    }
    fun addProducttoMyMarket(product:List<Product>){
        myMarketProducts.value=product
    }
    fun searchWithKeyword(text: String) {
        searchingKeyword.value = text
    }
    fun saveOrders(order: List<Order>) {
        orders.value = order
    }
    fun saveOrderItemCount(order_count: Int ){
        order_item_count.value = order_count
    }
    fun keepProductToEdit(product:Product){
        savedProductToAdd.value = product
    }
    fun saveOrderToAdd(order:Order){
        orderToAdd.value = order
    }
}