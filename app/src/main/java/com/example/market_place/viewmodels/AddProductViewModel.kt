package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.AddProductRequest
import com.example.market_place.model.ProductHelper
import com.example.market_place.repository.Repository


class AddProductViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var product = MutableLiveData<ProductHelper>()
    init {
        product.value = ProductHelper()
    }
    suspend fun addProduct() {

        val request = AddProductRequest( product.value!!.uploadImages,
                product.value!!.title,
                product.value!!.description,
                product.value!!.price_per_unit,
                product.value!!.units,
                product.value!!.is_active,
                product.value!!.rating,
                product.value!!.amount_type,
                product.value!!.price_type)

        try {
            Log.d("xxx", "MyApplication - add product response:  ${MarketPlaceApplication.token}")

            val result = repository.addProduct(MarketPlaceApplication.token, request)
            Log.d("xxx", "MyApplication - add product response:  ${result}")
        } catch (e: Exception) {
            Log.d("xxx", "AddProductViewModel - add product exception: ${e.toString()}")
        }
        TODO("Why always get timeout exception even if the token and the request is fine")
    }
    suspend fun removeProduct(product_id:String) {
        try {
            val result = repository.removeProduct(product_id)
            Log.d("xxx", "MyApplication - add product response:  ${result.message}")
        } catch (e: Exception) {
            Log.d("xxx", "AddProductViewModel - remove product exception: ${e.toString()}")
        }
    }
}
