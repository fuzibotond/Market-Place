package com.example.market_place.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.market_place.model.Product

class SharedViewModel : ViewModel() {
    private val _detailsProduct = MutableLiveData<Product>()
    private var detailsProduct: Product? = null

    fun saveDetailsProduct(product:Product){
        detailsProduct = product

    }
    fun getProduct():Product?{
        return detailsProduct
    }



}