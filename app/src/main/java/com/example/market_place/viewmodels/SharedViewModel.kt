package com.example.market_place.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.model.Order
import com.example.market_place.model.Product
import com.example.market_place.model.User

class SharedViewModel : ViewModel() {
    val detailsProduct = MutableLiveData<Product>()
    val myMarketProducts = MutableLiveData<List<Product>>()
    val searchingKeyword = MutableLiveData<String>()
    val orders = MutableLiveData<List<Order>>()
    val order_item_count = MutableLiveData<Int>()
    val savedProductToAdd = MutableLiveData<Product>()
    val orderToAdd = MutableLiveData<Order>()
    val detailedUser = MutableLiveData<String>()
    val UPDATE_PRODUCT_FLAG = MutableLiveData<Boolean>()
    val orderIsAcceptedIndicitaor = MutableLiveData<Boolean>()
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
    fun saveDetailedUser(user:String){
        detailedUser.value = user
    }
    fun setStateIfUpdateable(is_product_for_update:Boolean){
        UPDATE_PRODUCT_FLAG.value = is_product_for_update
    }
    fun saveOrderState(state:Boolean){
        orderIsAcceptedIndicitaor.value = state
    }

}