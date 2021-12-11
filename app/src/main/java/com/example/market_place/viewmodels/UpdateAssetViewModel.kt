package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.*
import com.example.market_place.repository.Repository


class UpdateAssetViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var product = MutableLiveData<Product>()
    var order_price = MutableLiveData<Int>()
    var order_title = MutableLiveData<String>()


    suspend fun updateProduct(product_id:String) {
        val request =
            UpdateProductRequest(product.value?.price_per_unit?.toInt(), product.value?.is_active, product.value?.title,product.value?.rating.toString(), product.value?.amount_type, product.value?.price_type )
        try {
            val result = repository.updateProduct(MarketPlaceApplication.token, request, product_id = product_id)

            Log.d("xxx", "MyApplication update product - code: ${result.updated_item} token:  ${MarketPlaceApplication.username}")
        } catch (e: Exception) {
            Log.d("xxx", "Update Product - exception: ${e.toString()}")
        }
    }
    suspend fun updateOrder(order_id:String, status:String) {
        val request =
            UpdateOrderRequest(order_price.value, status, order_title.value )
        try {
            val result = repository.updateOrder(MarketPlaceApplication.token, request,  order_id)

            Log.d("xxx", "MyApplication update order - code: ${result.timestamp} token:  ${MarketPlaceApplication.username}")
        } catch (e: Exception) {
            Log.d("xxx", "Update Order - exception: ${e.toString()}")
        }
    }
}
