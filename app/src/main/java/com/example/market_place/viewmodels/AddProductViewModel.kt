package com.example.market_place.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.AddProductRequest
import com.example.market_place.model.Product
import com.example.market_place.model.ProductHelper
import com.example.market_place.repository.Repository


class AddProductViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var product = MutableLiveData<ProductHelper>()
    var savedProduct = MutableLiveData<Product>()
    var productToRemove = MutableLiveData<String>()
    init {
        product.value = ProductHelper()
    }
    suspend fun addProduct() {
            Log.d("xxx", "Good to see you ")

        val request = AddProductRequest(
            product.value?.uploadImages,
            product.value?.title,
            product.value?.description,
            product.value?.price_per_unit,
            product.value?.units,
            product.value?.is_active,
            product.value?.rating,
            product.value?.amount_type,
            product.value?.price_type
        )
        Log.d("xxx", "MyApplication - add product request: ${product.value} ")
        try {
            val result = repository.addProduct(MarketPlaceApplication.token, request)
            Log.d("xxx", "MyApplication - add product response: ${result.creation} ")
            Toast.makeText(context, result.creation, Toast.LENGTH_SHORT).show()
            savedProduct.value = Product(
                result.rating,
                result.amount_type,
                result.price_type,
                result.product_id,
                result.username,
                result.is_active,
                result.price_per_unit,
                result.units,
                result.description,
                result.title,
                result.images,
                result.creation_time,
            )
        } catch (e: Exception) {
            Log.d("xxx", "AddProductViewModel - add product exception: ${e.toString()}")
        }

    }
    suspend fun removeProduct(product_id: String) {
        try {
            val result = repository.removeProduct(MarketPlaceApplication.token,product_id)
            Log.d("xxx", "MyApplication - add product response:  ${result.message}")
        } catch (e: Exception) {
            Log.d("xxx", "AddProductViewModel - remove product exception: ${e.toString()}")
        }
    }
}
