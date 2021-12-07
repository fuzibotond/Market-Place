package com.example.market_place.model


import com.squareup.moshi.JsonClass

data class ProductHelper(var rating: Double=0.0,
                   var amount_type: String="",
                   var price_type: String="",
                   var product_id: String="",
                   var username: String="",
                   var is_active: Boolean=false,
                   var price_per_unit: String="",
                   var units: String="",
                   var description: String="",
                   var title: String="",
                         var uploadImages: List<Image>?=null
)
@JsonClass(generateAdapter = true)
data class Image(val _id: String, val image_id: String, val image_name: String, val image_path: String)

@JsonClass(generateAdapter = true)
data class Product(val rating: Double,
                   val amount_type: String,
                   val price_type: String,
                   val product_id: String,
                   val username: String,
                   val is_active: Boolean,
                   val price_per_unit: String,
                   val units: String,
                   val description: String,
                   val title: String,
                   val images: List<Image>,
                   val creation_time: Long
)

@JsonClass(generateAdapter = true)
data class ProductResponse(val item_count: Int, val products: List<Product>, val timestamp: Long)

@JsonClass(generateAdapter = true)
data class AddProductRequest(
    val uploadImages: List<Image>?,
    val title: String,
    val description: String,
    val price_per_unit: String,
    val units: String,
    val is_active: Boolean,
    val rating: Double,
    val amount_type: String,
    val price_type: String,
)
@JsonClass(generateAdapter = true)
data class RemoveProductResponse(
    val message: String,
    val product_id: String,
    val Deletion_time: Long,
)
@JsonClass(generateAdapter = true)
data class AddProductResponse(val creation:String,
    val rating: Double,
                   val amount_type: String,
                   val price_type: String,
                   val product_id: String,
                   val username: String,
                   val is_active: Boolean,
                   val price_per_unit: String,
                   val units: String,
                   val description: String,
                   val title: String,
                   val images: List<Image>,
                   val creation_time: Long
)