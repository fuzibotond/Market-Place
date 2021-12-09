package com.example.market_place.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.model.Order
import com.example.market_place.model.Product

class SharedViewModel : ViewModel() {
    private var detailsProduct: Product? = null
    val myMarketProducts = mutableListOf<Product>()
    val searchingKeyword = MutableLiveData<String>()
    val orders = MutableLiveData<List<Order>>()
    fun saveDetailsProduct(product:Product){
        detailsProduct = product
    }
    fun getProduct():Product?{
        return detailsProduct
    }
    fun addProducttoMyMarket(product:Product){
        myMarketProducts.add(product)
    }
    fun getAllMyproducts():MutableList<Product>?{
        return myMarketProducts!!
    }
    fun searchWithKeyword(text: String) {
        searchingKeyword.value = text
    }
    fun saveOrders(order: List<Order>) {
        orders.value = order
    }



}