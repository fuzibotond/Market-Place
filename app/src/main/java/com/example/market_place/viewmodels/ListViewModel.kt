package com.example.market_place.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.market_place.MarketPlaceApplication
import com.example.market_place.model.Product
import com.example.market_place.repository.Repository
import kotlinx.coroutines.launch


class ListViewModel(private val repository: Repository) : ViewModel() {
    var products = MutableLiveData<List<Product>>()

    init {
        Log.d("xxx", "ListViewModel constructor - Token: ${MarketPlaceApplication.token}")
        getProducts(200, 50)
    }

    fun getProducts(limit: Int, skip: Int) {
        viewModelScope.launch {
            try {
                val result =
                    repository.getProducts(MarketPlaceApplication.token, limit, skip)
                products.value = result.products
                Log.d("xxx", "ListViewModel - #products:  ${result.item_count}")
            } catch (e: Exception) {
                Log.d("xxx", "ListViewMofdel exception: ${e.toString()}")
            }
        }
    }
}
