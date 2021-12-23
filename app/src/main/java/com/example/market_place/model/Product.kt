package com.example.market_place.model


import com.squareup.moshi.JsonClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
data class Message(
    var username: String,
    var message_id: String,
    var message: String,
    var creation_time: Long
)
data class ProductHelper(var rating: Double?=0.0,
                   var amount_type: String?="",
                   var price_type: String?="",
                   var product_id: String?="",
                   var username: String?="",
                   var is_active: Boolean?=false,
                   var price_per_unit: String?="",
                   var units: String?="",
                   var description: String?="",
                   var title: String?="",
                         var uploadImages: List<Image>?=null
)
data class OrderHelper(
                         var price_per_unit: String?="",
                         var units: String?="",
                         var description: String?="",
                         var title: String?="",
                         var uploadImages: List<Image>?=null,
                            var owner_username: String?=""
)
data class UpdatedProduct(
    val rating: Int,
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
    val creation_time: Long,
    val messages: List<Message>
)
data class UpdatedOrder(
    val status: String,
    val order_id: String,
    val username: String,
    val owner_username: String,
    val price_per_unit: String,
    val units: String,
    val description: String,
    val title: String,
    val images: List<Image>,
    val messages: List<Message>
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
    val title: String?,
    val description: String?,
    val price_per_unit: String?,
    val units: String?,
    val is_active: Boolean?,
    val rating: Double?,
    val amount_type: String?,
    val price_type: String?,
)
@JsonClass(generateAdapter = true)
data class RemoveProductResponse(
    val message: String,
    val product_id: String,
    val deletion_time: Long,
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
@JsonClass(generateAdapter = true)
data class OrderResponse(val item_count: Int, val orders: List<Order>, val timestamp: Long)


@JsonClass(generateAdapter = true)
data class Order(  val owner_username: String,
                   val order_id: String,
                   val username: String,
                   val messages: List<Message>,
                   val price_per_unit: String,
                   val units: String,
                   val description: String,
                   val title: String,
                   val images: List<Image>,
                   val creation_time: Long,
                    val status: String
)
@JsonClass(generateAdapter = true)
data class AddOrderRequest(
    val uploadImages: List<Image>?,
    val title: String?,
    val description: String?,
    val price_per_unit: String?,
    val units: String?,
    val owner_username: String?,
)
@JsonClass(generateAdapter = true)
data class OrderResponseCode(
    val creation: String,
    val order_id: String,
    val username: String,
    val status: String,
    val owner_username: String,
    val price_per_unit: String
    )

@JsonClass(generateAdapter = true)
data class UpdateProductRequest(
    val price_per_unit: Int?,
    val is_active: Boolean?,
    val title: String?,
    val rating: String?,
    val amount_type: String?,
    val price_type: String?,
)
@JsonClass(generateAdapter = true)
data class UpdateProductResponse(val updated_item: UpdatedProduct )

@JsonClass(generateAdapter = true)
data class UpdateOrderRequest(
    val price_per_unit: Int?,
    val status: String?,
    val title: String?,
)

@JsonClass(generateAdapter = true)
data class UpdateOrderResponse(val timestamp: Long )

@JsonClass(generateAdapter = true)
data class RemoveOrderResponse(
    val message: String,
    val order_id: String,
    val deletion_time: Long,
)

@JsonClass(generateAdapter = true)
data class GetMessagesResponse(
    val item_count: Int,
    val messages: List<Message>,
    val timestamp: Long,
)

@JsonClass(generateAdapter = true)
data class AddMessageToOrderResponse(val updated_item: UpdatedOrder )

@JsonClass(generateAdapter = true)
data class AddMessageToProductResponse(val updated_item: UpdatedProduct )

@JsonClass(generateAdapter = true)
data class AddMessageRequest(val message: String )