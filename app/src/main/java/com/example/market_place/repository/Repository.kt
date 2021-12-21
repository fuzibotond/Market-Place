package com.example.market_place.repository

import com.example.market_place.api.RetrofitInstance
import com.example.market_place.model.*
import kotlinx.coroutines.processNextEventInCurrentThread

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }
    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }

    suspend fun resetPassword(request: ResetPasswordRequest): ResetPasswordResponse {
        return RetrofitInstance.api.resetPassword(request)
    }

    suspend fun getProducts(token: String, limit:Int, skip:Int): ProductResponse {
        return RetrofitInstance.api.getProducts(token, limit, skip)
    }

    suspend fun getUserInfo(username: String): UserInfoResponse {
        return RetrofitInstance.api.getUserInfo(username)
    }

    suspend fun updateUserData(token:String, request: UpdateUserDataRequest): UpdateUserDataResponse {
        return RetrofitInstance.api.updateUserData(token, request)
    }

    suspend fun getRefreshToken(token: String): RefreshTokenResponse {
        return RetrofitInstance.api.getRefreshToken(token)
    }

    suspend fun addProduct(token:String, request: AddProductRequest): AddProductResponse {
        return RetrofitInstance.api.addProduct(
            token,
            request.title,
            request.description,
            request.price_per_unit,
            request.units,
            request.is_active,
            request.rating,
            request.amount_type,
            request.price_type
        )
    }

    suspend fun removeProduct(token: String, product_id: String ): RemoveProductResponse {
        return RetrofitInstance.api.removeProduct(token,product_id)
    }
    suspend fun getOrders(token: String, limit:Int): OrderResponse {
        return RetrofitInstance.api.getOrders(token, limit)
    }
    suspend fun addOrder(token:String, request: AddOrderRequest): OrderResponseCode {
        return RetrofitInstance.api.addOrder(
            token,
            request.title,
            request.description,
            request.price_per_unit,
            request.units,
            request.owner_username
        )
    }
    suspend fun updateProduct(token:String, request: UpdateProductRequest, product_id: String): UpdateProductResponse {
        return RetrofitInstance.api.updateProduct(token, request, product_id)
    }
    suspend fun updateOrder(token:String, request: UpdateOrderRequest, order_id:String): UpdateOrderResponse {
        return RetrofitInstance.api.updateOrder(token,request, order_id)
    }
    suspend fun removeOrder(token: String, order_id: String ): RemoveOrderResponse {
        return RetrofitInstance.api.removeOrder(token,order_id)
    }
}