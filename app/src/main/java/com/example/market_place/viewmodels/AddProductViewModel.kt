package com.example.market_place.viewmodels

import com.example.market_place.model.Product

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.AddProductRequest
import com.example.market_place.repository.Repository


class AddProductViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var product = MutableLiveData<AddProductRequest>()
    var registratedProduct = MutableLiveData<Product>()
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
            val result = repository.addProduct(MarketPlaceApplication.token, request)
            registratedProduct.value = result
            Log.d("xxx", "MyApplication - add product response:  ${result}")
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}
