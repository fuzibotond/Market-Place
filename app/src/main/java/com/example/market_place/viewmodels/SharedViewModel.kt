package com.example.market_place.viewmodels

import androidx.lifecycle.ViewModel
import com.example.market_place.model.Product

class SharedViewModel : ViewModel() {
    private var detailsProduct: Product? = null
    private var myMarketProducts:MutableList<Product>? = null

    fun saveDetailsProduct(product:Product){
        detailsProduct = product
    }
    fun getProduct():Product?{
        return detailsProduct
    }
    fun addProducttoMyMarket(product:Product){
        myMarketProducts?.add(product)
    }
    fun getAllMyproducts():List<Product>?{
        return myMarketProducts
    }


}