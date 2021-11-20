package com.example.market_place.repository

import com.example.market_place.api.RetrofitInstance
import com.example.market_place.model.*

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

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token)
    }
}